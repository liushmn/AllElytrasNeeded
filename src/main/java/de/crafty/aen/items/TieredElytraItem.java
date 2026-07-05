package de.crafty.aen.items;

import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.config.SlotConfig;
import de.crafty.aen.elytra.ElytraComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TieredElytraItem extends ElytraItem {

    private final Tier tier;

    public TieredElytraItem(Tier tier, Properties properties) {
        super(properties);

        this.tier = tier;
    }

    public Tier getTier() {
        return this.tier;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        TieredElytraItem.appendElytraLines(itemStack, list);

    }

    public static void appendElytraLines(ItemStack stack, List<Component> list) {
        ElytraComponent elytraComponent = TieredElytraItem.getOrCreateElytraComponent(stack);
        int maxEffects = AENConfigs.SLOT_CONFIG.getMaxEffects(elytraComponent.tier());
        List<ElytraComponent.EffectSlot> slots = elytraComponent.slots();

        slots.forEach(slot -> {
            Component slotComp = Component.translatable("aen.effect_slot.prefix").append(": ").withStyle(ChatFormatting.GRAY);
            Component effectComp = Component.translatable(slot.effect().getDescriptionId()).withStyle(slot.color());
            Component levelComp = Component.literal(" - " + slot.level()).withStyle(ChatFormatting.GRAY);
            Component chargeComp = Component.literal(" (" + new DecimalFormat("0.0").format(slot.charge() / 20.0F) + "s)").withStyle(ChatFormatting.DARK_PURPLE);

            list.add(Component.empty().append(slotComp).append(effectComp).append(levelComp).append(chargeComp));
        });

        for (int i = 0; i < maxEffects - slots.size(); i++) {
            Component slotComp = Component.translatable("aen.effect_slot.prefix").append(": ").withStyle(ChatFormatting.GRAY);
            Component effectComp = Component.translatable("aen.effect_slot.empty").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC);

            list.add(Component.empty().append(slotComp).append(effectComp));
        }
    }

    public static boolean containsElytraComponent(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains("elytra_component")) || stack.getItem() instanceof TieredElytraItem || stack.getItem() == Items.ELYTRA;
    }

    public static ElytraComponent getOrCreateElytraComponent(ItemStack stack) {
        return ElytraComponent.fromStackOrCreate(stack);
    }

    @Nullable
    public static ElytraComponent getElytraComponent(ItemStack stack) {
        return ElytraComponent.fromStack(stack);
    }

    public static void updateElytraComponent(ItemStack stack, ElytraComponent component) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put("elytra_component", component.toTag());
        stack.setTag(tag);
    }


    public enum Tier {
        VANILLA(new ResourceLocation("textures/entity/elytra.png"), null, ChatFormatting.DARK_GRAY),
        IRON(new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/iron_elytra.png"), new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/iron_elytra_topper.png"), ChatFormatting.WHITE),
        GOLD(new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/gold_elytra.png"), new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/gold_elytra_topper.png"), ChatFormatting.GOLD),
        DIAMOND(new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/diamond_elytra.png"), new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/diamond_elytra_topper.png"), ChatFormatting.AQUA),
        NETHERITE(new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/netherite_elytra.png"), new ResourceLocation(AllElytrasNeeded.MOD_ID, "textures/entity/netherite_elytra_topper.png"), ChatFormatting.DARK_PURPLE);

        final ResourceLocation texture, secondaryTexture;
        final ChatFormatting color;

        Tier(ResourceLocation texture, ResourceLocation secondaryTexture, ChatFormatting color) {
            this.texture = texture;
            this.secondaryTexture = secondaryTexture;
            this.color = color;
        }

        public ResourceLocation texture() {
            return this.texture;
        }

        public ResourceLocation secondaryTexture() {
            return this.secondaryTexture;
        }

        public ChatFormatting color() {
            return this.color;
        }
    }
}
