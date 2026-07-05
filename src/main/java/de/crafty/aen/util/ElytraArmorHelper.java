package de.crafty.aen.util;

import de.crafty.aen.init.TagRegistry;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraArmorHelper {


    public static boolean canMerge(ItemStack elytraStack, ItemStack chestplateStack) {
        if(!chestplateStack.is(TagRegistry.ELYTRA_COMPATIBLE_CHESTPLATES))
            return false;

        if(!elytraStack.is(Items.ELYTRA) && !(elytraStack.getItem() instanceof TieredElytraItem))
            return false;

        return TieredElytraItem.getElytraComponent(chestplateStack) == null;
    }

    public static void merge(ItemStack elytraStack, ItemStack chestplateStack) {
        if(!ElytraArmorHelper.canMerge(elytraStack, chestplateStack))
            return;

        TieredElytraItem.updateElytraComponent(chestplateStack, TieredElytraItem.getOrCreateElytraComponent(elytraStack));
    }
}
