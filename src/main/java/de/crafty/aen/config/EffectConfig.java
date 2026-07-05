package de.crafty.aen.config;

import com.google.gson.JsonArray;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class EffectConfig extends AbstractAENConfig {

    protected EffectConfig() {
        super("effectConfig");
    }


    private List<MobEffect> allowedEffects;

    @Override
    protected void setDefaults() {
        this.allowedEffects = new ArrayList<>();

        this.addAllowedEffect(MobEffects.ABSORPTION);
        this.addAllowedEffect(MobEffects.FIRE_RESISTANCE);
        this.addAllowedEffect(MobEffects.INVISIBILITY);
        this.addAllowedEffect(MobEffects.JUMP);
        this.addAllowedEffect(MobEffects.NIGHT_VISION);
        this.addAllowedEffect(MobEffects.POISON);
        this.addAllowedEffect(MobEffects.REGENERATION);
        this.addAllowedEffect(MobEffects.DAMAGE_RESISTANCE);
        this.addAllowedEffect(MobEffects.SLOW_FALLING);
        this.addAllowedEffect(MobEffects.MOVEMENT_SLOWDOWN);
        this.addAllowedEffect(MobEffects.MOVEMENT_SPEED);
        this.addAllowedEffect(MobEffects.DAMAGE_BOOST);
        this.addAllowedEffect(MobEffects.WATER_BREATHING);
        this.addAllowedEffect(MobEffects.WEAKNESS);

        this.data().addProperty("allowMultiPotions", false);
    }


    private void addAllowedEffect(MobEffect effect) {
        this.allowedEffects.add(effect);
    }

    public List<MobEffect> getAllowedEffects() {
        return this.allowedEffects;
    }

    public boolean isMultiPotionAllowed() {
        return this.data().get("allowMultiPotions").getAsBoolean();
    }


    @Override
    public void load() {
        super.load();

        this.allowedEffects.clear();
        JsonArray effectList = this.data().getAsJsonArray("allowedEffects");
        effectList.forEach(jsonElement -> {
            if (BuiltInRegistries.MOB_EFFECT.containsKey(ResourceLocation.tryParse(jsonElement.getAsString())))
                this.addAllowedEffect(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(jsonElement.getAsString())));
        });

        LOGGER.info("{} allowed effects present", this.allowedEffects.size());
    }

    @Override
    public void save() {
        JsonArray effectList = new JsonArray();
        this.allowedEffects.forEach(effect -> {
            effectList.add(BuiltInRegistries.MOB_EFFECT.getKey(effect).toString());
        });
        this.data().add("allowedEffects", effectList);

        super.save();
    }
}
