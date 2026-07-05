package de.crafty.aen.config;

import java.util.ArrayList;
import java.util.List;

public class AENConfigs {

    private static final List<AbstractAENConfig> CONFIGS = new ArrayList<>();

    public static final SlotConfig SLOT_CONFIG = register(new SlotConfig());
    public static final EffectConfig EFFECT_CONFIG = register(new EffectConfig());
    public static final ChestplateConfig CHESTPLATE_CONFIG = register(new ChestplateConfig());


    private static <T extends AbstractAENConfig> T register(T config) {
        CONFIGS.add(config);
        return config;
    }


    public static List<AbstractAENConfig> all() {
        return CONFIGS;
    }
}
