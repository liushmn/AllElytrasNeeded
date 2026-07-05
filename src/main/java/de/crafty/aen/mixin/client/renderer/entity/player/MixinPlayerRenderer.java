package de.crafty.aen.mixin.client.renderer.entity.player;

import de.crafty.aen.layers.ElytraSecondaryLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public MixinPlayerRenderer(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }


    /**
     * Adds a secondary elytra layer
     * @param context
     * @param bl
     * @param ci
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void addSecondaryLayer(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci){
        this.addLayer(new ElytraSecondaryLayer<>(this, context.getModelSet()));
    }
}
