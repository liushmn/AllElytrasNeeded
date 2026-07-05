package de.crafty.aen.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.crafty.aen.AllElytrasNeeded;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AbstractAENConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AllElytrasNeeded.MOD_ID + "[CONFIG]");


    private JsonObject data;
    private final File file;

    protected AbstractAENConfig(String name) {
        this.data = new JsonObject();
        this.file = new File("config/" + AllElytrasNeeded.MOD_ID, name + ".json");
        this.setDefaults();
    }


    protected abstract void setDefaults();

    protected JsonObject data() {
        return this.data;
    }

    public void load() {
        if(!this.file.exists()){
            this.save();
            return;
        }

        try {
            String content = FileUtils.readFileToString(this.file, StandardCharsets.UTF_8);
            this.data = JsonParser.parseString(content).getAsJsonObject();
        } catch (Exception e) {
            LOGGER.error("Failed to load config file", e);
        }
    }

    public void save() {
        if(this.file.getParentFile().mkdirs())
            LOGGER.info("Config folder does not exist, creating one...");

        try {

            if(this.file.createNewFile())
                LOGGER.info("Config file {} does not exist, creating one...", this.file.getName());

        } catch (IOException e) {
            LOGGER.error("Failed to create config file", e);
        }

        try {
            FileUtils.writeStringToFile(this.file, new GsonBuilder().setPrettyPrinting().create().toJson(this.data), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Failed to save config file", e);
        }
    }
}
