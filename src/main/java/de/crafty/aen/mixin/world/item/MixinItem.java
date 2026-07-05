package de.crafty.aen.mixin.world.item;

import de.crafty.aen.init.TagRegistry;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem implements FeatureElement, ItemLike {

    @Shadow
    public abstract String getDescriptionId();

    @Inject(method = "getDescriptionId(Lnet/minecraft/world/item/ItemStack;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    private void addElytraChestplateName(ItemStack itemStack, CallbackInfoReturnable<String> cir){
        if(itemStack.is(TagRegistry.ELYTRA_COMPATIBLE_CHESTPLATES) && TieredElytraItem.containsElytraComponent(itemStack))
            cir.setReturnValue(this.getDescriptionId() + ".elytra");
    }

}
