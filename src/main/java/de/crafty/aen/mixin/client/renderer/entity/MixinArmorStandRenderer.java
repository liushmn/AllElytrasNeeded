package de.crafty.aen.mixin.client.renderer.entity;

import de.crafty.aen.layers.ElytraSecondaryLayer;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public abstract class MixinArmorStandRenderer extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {


    public MixinArmorStandRenderer(EntityRendererProvider.Context context, ArmorStandArmorModel entityModel, float f) {
        super(context, entityModel, f);
    }

    /**
     * Adds a secondary elytra layer
     * @param context
     * @param ci
     */

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addSecondaryLayer(EntityRendererProvider.Context context, CallbackInfo ci){
        this.addLayer(new ElytraSecondaryLayer<>(this, context.getModelSet()));
    }
}
