package de.crafty.aen;

import de.crafty.aen.command.AENCommand;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.config.AbstractAENConfig;
import de.crafty.aen.config.SlotConfig;
import de.crafty.aen.config.EffectConfig;
import de.crafty.aen.init.ItemRegistry;
import de.crafty.aen.init.RecipeSerializerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO add advancement compat
public class AllElytrasNeeded implements ModInitializer {

    public static final String MOD_ID = "aen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Minecraft!");

        ItemRegistry.perform();
        RecipeSerializerRegistry.perform();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> AENCommand.register(dispatcher));

        AENConfigs.all().forEach(AbstractAENConfig::load);

    }

}
