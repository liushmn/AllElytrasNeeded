package de.crafty.aen.mixin.client.renderer.entity;

import de.crafty.aen.layers.ElytraSecondaryLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class MixinHumanoidMobRenderer<T extends Mob, M extends HumanoidModel<T>> extends MobRenderer<T, M> {

    public MixinHumanoidMobRenderer(EntityRendererProvider.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }


    /**
     * Adds a secondary elytra layer
     * @param context
     * @param humanoidModel
     * @param f
     * @param g
     * @param h
     * @param i
     * @param ci
     */
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;FFFF)V", at = @At("RETURN"))
    private void addSecondaryLayer(EntityRendererProvider.Context context, HumanoidModel humanoidModel, float f, float g, float h, float i, CallbackInfo ci){
        this.addLayer(new ElytraSecondaryLayer<>(this, context.getModelSet()));
    }

}
