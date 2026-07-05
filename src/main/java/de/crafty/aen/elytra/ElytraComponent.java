package de.crafty.aen.elytra;

import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElytraComponent {


    private final List<EffectSlot> slots = new ArrayList<>();
    private final TieredElytraItem.Tier tier;
    private final ItemStack stack;

    private ElytraComponent(ItemStack stack, TieredElytraItem.Tier tier) {
        this.stack = stack;
        this.tier = tier;
    }

    private ElytraComponent(ItemStack stack, TieredElytraItem.Tier tier, List<EffectSlot> slots) {
        this(stack, tier);
        this.slots.addAll(slots);
    }

    public TieredElytraItem.Tier tier() {
        return this.tier;
    }

    public List<EffectSlot> slots() {
        return this.slots;
    }

    public void tick() {
        this.slots.forEach(EffectSlot::tick);
        if (!this.slots.isEmpty())
            TieredElytraItem.updateElytraComponent(this.stack, this);
    }

    public void applyEffects(Player player) {
        this.slots.forEach(effectSlot -> {
            if (effectSlot.charge() <= 0.0F)
                return;
            player.addEffect(new MobEffectInstance(effectSlot.effect(), (int) effectSlot.charge(), effectSlot.level() - 1));
            effectSlot.reset();
        });

        TieredElytraItem.updateElytraComponent(this.stack, this);
    }


    public boolean canApplyEffect(ItemStack potionStack) {
        Potion potion = PotionUtils.getPotion(potionStack);

        if (potion == Potions.EMPTY || potion.getEffects().isEmpty())
            return false;

        HashMap<MobEffect, Integer> effect = new HashMap<>();
        potion.getEffects().forEach(effectInstance -> effect.put(effectInstance.getEffect(), effectInstance.getAmplifier() + 1));
        return this.canApplyEffects(effect);
    }

    /**
     * Returns whether all effects from the list can be applied to the elytra
     */
    private boolean canApplyEffects(HashMap<MobEffect, Integer> effects) {
        if (!AENConfigs.EFFECT_CONFIG.isMultiPotionAllowed() && effects.size() > 1)
            return false;

        int fittingEffects = this.getApplicableEffects(effects);
        if (fittingEffects == 0)
            return false;

        return AENConfigs.SLOT_CONFIG.getMaxEffects(this.tier()) - this.slots().size() >= fittingEffects;
    }


    /**
     * Returns the number of effects from the list that can be applied to the elytra
     *
     * @param effects the effect list
     */
    private int getApplicableEffects(HashMap<MobEffect, Integer> effects) {

        int validEffects = 0;

        for (MobEffect effect : effects.keySet()) {
            int level = effects.get(effect);

            if (effect.isInstantenous())
                continue;

            if (!AENConfigs.EFFECT_CONFIG.getAllowedEffects().contains(effect))
                continue;

            List<EffectSlot> slots = this.slots();
            for (EffectSlot slot : new ArrayList<>(slots)) {
                if (slot.effect() == effect && slot.level() >= level)
                    continue;

                if (slot.effect() == effect)
                    slots.remove(slot);
            }

            if (slots.size() >= AENConfigs.SLOT_CONFIG.getMaxEffects(this.tier()))
                continue;

            if (level <= AENConfigs.SLOT_CONFIG.getMaxLevel(this.tier()))
                validEffects++;
        }

        return validEffects;
    }

    public void addEffectSlot(ItemStack potionStack) {
        Potion potion = PotionUtils.getPotion(potionStack);
        if (potion == Potions.EMPTY || potion.getEffects().isEmpty())
            return;

        HashMap<MobEffect, Integer> effect = new HashMap<>();
        potion.getEffects().forEach(effectInstance -> effect.put(effectInstance.getEffect(), effectInstance.getAmplifier() + 1));
        this.addEffectSlot(effect);
    }

    private void addEffectSlot(HashMap<MobEffect, Integer> effects) {
        if (!this.canApplyEffects(effects))
            return;

        List<EffectSlot> slots = this.slots();

        effects.forEach((effect, level) -> {
            slots.removeIf(slot -> slot.effect() == effect);
            slots.add(new EffectSlot(this.tier(), effect, level));
        });

        TieredElytraItem.updateElytraComponent(stack, this);

    }


    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("tier", this.tier.name());
        ListTag effects = new ListTag();
        this.slots.forEach(effectSlot -> effects.add(effectSlot.toTag()));
        tag.put("effectSlots", effects);
        return tag;
    }

    public static ElytraComponent fromStack(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("elytra_component"))
            return null;

        CompoundTag elytraTag = tag.getCompound("elytra_component");

        ListTag effectSlots = elytraTag.getList("effectSlots", ListTag.TAG_COMPOUND);
        List<EffectSlot> slots = new ArrayList<>();
        effectSlots.stream().map(slotTag -> (CompoundTag) slotTag).forEach(slotTag -> slots.add(EffectSlot.fromTag(TieredElytraItem.Tier.valueOf(elytraTag.getString("tier")), slotTag)));
        return new ElytraComponent(stack, TieredElytraItem.Tier.valueOf(elytraTag.getString("tier")), slots);
    }

    public static ElytraComponent fromStackOrCreate(ItemStack stack) {
        ElytraComponent component = fromStack(stack);
        if (component != null)
            return component;

        TieredElytraItem.Tier tier = stack.getItem() == Items.ELYTRA ? TieredElytraItem.Tier.VANILLA : ((TieredElytraItem) stack.getItem()).getTier();

        return new ElytraComponent(stack, tier);
    }


    public static class EffectSlot {

        private final TieredElytraItem.Tier tier;
        private final MobEffect effect;
        private final ChatFormatting color;
        private final int level;

        private float charge;

        private EffectSlot(TieredElytraItem.Tier tier, MobEffect effect, int level) {
            this.tier = tier;
            this.effect = effect;
            this.level = level;
            this.color = ElytraComponent.findEffectColor(effect);
        }

        private EffectSlot(TieredElytraItem.Tier tier, MobEffect effect, int level, float charge) {
            this(tier, effect, level);
            this.charge = charge;
        }

        public void tick() {
            this.charge += AENConfigs.SLOT_CONFIG.getChargeMultiplier(this.tier);
            this.charge = Math.min(this.charge, AENConfigs.SLOT_CONFIG.getMaxCharge(this.tier) * 20.0F);
        }

        public void reset() {
            this.charge = 0;
        }


        public MobEffect effect() {
            return this.effect;
        }

        public ChatFormatting color() {
            return this.color;
        }

        public int level() {
            return this.level;
        }

        public float charge() {
            return this.charge;
        }


        private CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("effect", BuiltInRegistries.MOB_EFFECT.getKey(this.effect).toString());
            tag.putInt("level", this.level);
            tag.putFloat("charge", this.charge);
            return tag;
        }

        public static EffectSlot fromTag(TieredElytraItem.Tier tier, CompoundTag tag) {
            if (!BuiltInRegistries.MOB_EFFECT.containsKey(ResourceLocation.tryParse(tag.getString("effect"))))
                return null;

            return new EffectSlot(tier, BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(tag.getString("effect"))), tag.getInt("level"), tag.getFloat("charge"));
        }

    }


    private static ChatFormatting findEffectColor(MobEffect effect) {

        List<Color> colors = new ArrayList<>();
        List<ChatFormatting> formattings = new ArrayList<>();

        for (ChatFormatting color : ChatFormatting.values()) {
            if (color.getColor() != null) {
                colors.add(new Color(color.getColor()));
                formattings.add(color);
            }
        }

        Color effectColor = new Color(effect.getColor());

        int minDiff = ElytraComponent.getColorDiff(effectColor, colors.get(0));
        ChatFormatting minDiffColor = formattings.get(0);

        for (int i = 1; i < colors.size(); i++) {
            Color color = colors.get(i);


            int diff = ElytraComponent.getColorDiff(effectColor, color);

            if (diff < minDiff) {
                minDiff = diff;
                minDiffColor = formattings.get(i);
            }
        }

        return minDiffColor;
    }

    private static int getColorDiff(Color color1, Color color2) {
        return (Math.abs(color1.getRed() - color2.getRed()) + Math.abs(color1.getGreen() - color2.getGreen()) + Math.abs(color1.getBlue() - color2.getBlue())) / 3;
    }
}
