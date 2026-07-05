package de.crafty.aen.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.items.TieredElytraItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SlotConfig extends AbstractAENConfig {

    protected SlotConfig() {
        super("slotConfig");
    }


    @Override
    protected void setDefaults() {
        this.data().add("tierConfig", new JsonObject());
        this.setTierConfig(TieredElytraItem.Tier.VANILLA, 0, 0, 0, 0.0F);
        this.setTierConfig(TieredElytraItem.Tier.IRON, 1, 1, 5, 0.5F);
        this.setTierConfig(TieredElytraItem.Tier.GOLD, 1, 2, 10, 0.75F);
        this.setTierConfig(TieredElytraItem.Tier.DIAMOND, 2, 1, 10, 0.75F);
        this.setTierConfig(TieredElytraItem.Tier.NETHERITE, 2, 2, 15, 1.0F);
    }

    private void setTierConfig(TieredElytraItem.Tier tier, int maxEffects, int maxLevel, int maxCharge, float chargeMultiplier) {
        JsonObject config = new JsonObject();
        config.addProperty("maxEffects", maxEffects);
        config.addProperty("maxLevel", maxLevel);
        config.addProperty("maxCharge", maxCharge);
        config.addProperty("chargeMultiplier", chargeMultiplier);
        this.data().getAsJsonObject("tierConfig").add(tier.name().toLowerCase(), config);
    }

    public int getMaxEffects(TieredElytraItem.Tier tier) {
        return this.data().getAsJsonObject("tierConfig").getAsJsonObject(tier.name().toLowerCase()).get("maxEffects").getAsInt();
    }

    public int getMaxLevel(TieredElytraItem.Tier tier) {
        return this.data().getAsJsonObject("tierConfig").getAsJsonObject(tier.name().toLowerCase()).get("maxLevel").getAsInt();
    }

    public int getMaxCharge(TieredElytraItem.Tier tier) {
        return this.data().getAsJsonObject("tierConfig").getAsJsonObject(tier.name().toLowerCase()).get("maxCharge").getAsInt();
    }

    public float getChargeMultiplier(TieredElytraItem.Tier tier) {
        return this.data().getAsJsonObject("tierConfig").getAsJsonObject(tier.name().toLowerCase()).get("chargeMultiplier").getAsFloat();
    }

}
