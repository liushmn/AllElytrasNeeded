package de.crafty.aen.init;

import de.crafty.aen.AllElytrasNeeded;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class TagRegistry {

    public static final TagKey<Item> TIERED_ELYTRA_ITEMS = TagKey.create(Registries.ITEM, new ResourceLocation(AllElytrasNeeded.MOD_ID, "drenchable_elytra_items"));
    public static final TagKey<Item> ELYTRA_COMPATIBLE_CHESTPLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AllElytrasNeeded.MOD_ID, "elytra_compatible_chestplates"));

}
