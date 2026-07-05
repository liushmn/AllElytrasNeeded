package de.crafty.aen.mixin.world.entity;

import de.crafty.aen.elytra.ElytraComponent;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.commands.CommandSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Entity.class)
public abstract class MixinEntity implements Nameable, EntityAccess, CommandSource {


    @Shadow
    @Final
    protected static int FLAG_FALL_FLYING;

    @Shadow
    private Level level;

    @Inject(method = "setSharedFlag", at = @At("RETURN"))
    private void applyChargedEffects(int i, boolean bl, CallbackInfo ci) {
        if (!((Object) this instanceof Player player))
            return;

        if (i != FLAG_FALL_FLYING || bl)
            return;

        if (this.level.isClientSide)
            return;

        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!TieredElytraItem.containsElytraComponent(chestStack))
            return;

        ElytraComponent component = TieredElytraItem.getOrCreateElytraComponent(chestStack);
        component.applyEffects(player);
    }

}
