package de.crafty.aen.mixin.world.entity.player;

import de.crafty.aen.elytra.ElytraComponent;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Implements elytra logic for tiered elytras
     */
    @Redirect(method = "tryToStartFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean addTieredElytra(ItemStack instance, Item item) {
        return instance.is(item) || TieredElytraItem.containsElytraComponent(instance);
    }


    @Inject(method = "tick", at = @At("RETURN"))
    private void chargePotionEffects(CallbackInfo ci) {
        if (this.level().isClientSide || !this.getSharedFlag(FLAG_FALL_FLYING))
            return;

        ItemStack chestStack = this.getItemBySlot(EquipmentSlot.CHEST);
        if (!TieredElytraItem.containsElytraComponent(chestStack))
            return;

        ElytraComponent component = TieredElytraItem.getOrCreateElytraComponent(chestStack);
        component.tick();

    }

}
