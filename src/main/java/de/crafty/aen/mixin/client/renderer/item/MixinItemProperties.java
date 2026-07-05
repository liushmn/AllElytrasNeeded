package de.crafty.aen.mixin.client.renderer.item;

import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.init.ItemRegistry;
import de.crafty.aen.init.TagRegistry;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemProperties.class)
public abstract class MixinItemProperties {


    @Shadow
    public static void register(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void registerTieredElytras(CallbackInfo ci) {
        register(ItemRegistry.IRON_ELYTRA, new ResourceLocation("broken"), (itemStack, clientLevel, livingEntity, i) -> ElytraItem.isFlyEnabled(itemStack) ? 0.0F : 1.0F);
        AENConfigs.CHESTPLATE_CONFIG.getElytraCompatible().forEach(item -> {
            register(item, new ResourceLocation(AllElytrasNeeded.MOD_ID, "elytra_container"), (itemStack, clientLevel, livingEntity, i) -> {
                return itemStack.hasTag() && itemStack.getTag().contains("elytra_component") ? 1.0F : 0.0F;
            });
        });
    }
}
