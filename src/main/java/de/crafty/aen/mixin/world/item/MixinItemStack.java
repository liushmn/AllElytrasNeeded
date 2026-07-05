package de.crafty.aen.mixin.world.item;

import de.crafty.aen.elytra.ElytraComponent;
import de.crafty.aen.init.TagRegistry;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {


    @Redirect(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"))
    private void addElytraTooltip(Item instance, ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        instance.appendHoverText(itemStack, level, list, tooltipFlag);

        if (!TieredElytraItem.containsElytraComponent(itemStack) || !itemStack.is(TagRegistry.ELYTRA_COMPATIBLE_CHESTPLATES))
            return;

        ElytraComponent elytraComponent = TieredElytraItem.getOrCreateElytraComponent(itemStack);
        TieredElytraItem.Tier tier = elytraComponent.tier();

        list.add(Component.empty().append(Component.translatable("aen.elytra_chestplate.tier").append(": ").withStyle(ChatFormatting.GRAY)).append(Component.translatable("aen.elytra_chestplate.tier." + tier.name().toLowerCase()).withStyle(tier.color())));
        TieredElytraItem.appendElytraLines(itemStack, list);
    }
}
