package de.crafty.aen.mixin.client.color.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemColors.class)
public abstract class MixinItemColors {

    @Redirect(method = "createDefault", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;register(Lnet/minecraft/client/color/item/ItemColor;[Lnet/minecraft/world/level/ItemLike;)V", ordinal = 0))
    private static void changeLeatherChestplateColor(ItemColors instance, ItemColor itemColor, ItemLike[] itemLikes) {

        for(ItemLike itemLike : itemLikes){
            if(itemLike.asItem() == Items.LEATHER_CHESTPLATE)
                instance.register((itemStack, i) -> {
                    if(itemStack.hasTag() && itemStack.getTag().contains("elytra_container"))
                        return i == 1 ? ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack) : -1;
                    else
                        return i == 0 ? ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack) : -1;

                }, Items.LEATHER_CHESTPLATE);
        }

    }


}
