package de.crafty.aen.mixin.client.gui.screen.inventory;

import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingScreen.class)
public abstract class MixinSmithingScreen extends ItemCombinerScreen<SmithingMenu> {


    @Shadow
    private @Nullable ArmorStand armorStandPreview;

    public MixinSmithingScreen(SmithingMenu itemCombinerMenu, Inventory inventory, Component component, ResourceLocation resourceLocation) {
        super(itemCombinerMenu, inventory, component, resourceLocation);
    }


    @Redirect(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventory(Lnet/minecraft/client/gui/GuiGraphics;IIILorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void rotateElytraPreview(GuiGraphics guiGraphics, int i, int j, int k, Quaternionf quaternionf, Quaternionf quaternionf2, LivingEntity livingEntity){

        if(this.armorStandPreview != null && TieredElytraItem.containsElytraComponent(this.armorStandPreview.getItemBySlot(EquipmentSlot.CHEST)))
            quaternionf = new Quaternionf().set(quaternionf).rotateY((float) Math.toRadians(-105));

        InventoryScreen.renderEntityInInventory(guiGraphics, i, j, k, quaternionf, quaternionf2, livingEntity);
    }

    @Inject(method = "updateArmorStandPreview", at = @At("RETURN"))
    private void renderTieredElytra(ItemStack itemStack, CallbackInfo ci) {
        if (itemStack.getItem() instanceof TieredElytraItem && this.armorStandPreview != null) {

            this.armorStandPreview.setItemSlot(EquipmentSlot.CHEST, itemStack.copy());
            this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
    }
}
