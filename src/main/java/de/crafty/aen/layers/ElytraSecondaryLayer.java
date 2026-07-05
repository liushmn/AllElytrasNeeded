package de.crafty.aen.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.crafty.aen.elytra.ElytraComponent;
import de.crafty.aen.items.TieredElytraItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/*
    A new layer that adds a secondary layer to the elytra
 */
@Environment(EnvType.CLIENT)
public class ElytraSecondaryLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final ElytraModel<T> elytraModel;

    public ElytraSecondaryLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);

        this.elytraModel = new ElytraModel<>(entityModelSet.bakeLayer(ModelLayers.ELYTRA));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if(!TieredElytraItem.containsElytraComponent(stack)) return;

        ElytraComponent component = TieredElytraItem.getOrCreateElytraComponent(stack);
        if(component.tier().secondaryTexture() == null)
            return;

        poseStack.pushPose();
        poseStack.translate(0.0D, -0.0625D / 4.0F, 0.125D);
        poseStack.scale(1.0F + 0.0625F / 4.0F, 1.0F + 0.0625F / 2.0F, 1.0F + 0.0625F / 2.0F);
        this.getParentModel().copyPropertiesTo(this.elytraModel);
        this.elytraModel.setupAnim(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer1 = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(component.tier().secondaryTexture()), false, stack.hasFoil());
        this.elytraModel.renderToBuffer(poseStack, vertexConsumer1, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
