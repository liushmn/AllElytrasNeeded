package de.crafty.aen.config;

import com.google.gson.JsonArray;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ChestplateConfig extends AbstractAENConfig {



    public ChestplateConfig() {
        super("chestplateConfig");
    }

    @Override
    protected void setDefaults() {
        JsonArray array = new JsonArray();
        array.add("minecraft:elytra");
        array.add("aen:iron_elytra");
        this.data().add("elytra_compatible", array);
    }

    public List<Item> getElytraCompatible(){
        JsonArray array = this.data().getAsJsonArray("elytra_compatible");
        List<Item> compatible = Lists.newArrayList();
        array.forEach(jsonElement -> {
            if(BuiltInRegistries.ITEM.containsKey(ResourceLocation.tryParse(jsonElement.getAsString())))
                compatible.add(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(jsonElement.getAsString())));
        });
        return compatible;
    }

}
