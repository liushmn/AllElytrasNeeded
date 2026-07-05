package de.crafty.aen.mixin.client.resources.model;

import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.init.TagRegistry;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class MixinModelBakery {


    @Redirect(method = "loadBlockModel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/model/BlockModel;name:Ljava/lang/String;", opcode = Opcodes.PUTFIELD))
    private void addElytraOverride(BlockModel instance, String value) {
        instance.name = value;
        ResourceLocation location = ResourceLocation.tryParse(value);
        if (location == null) return;


        AENConfigs.CHESTPLATE_CONFIG.getElytraCompatible().forEach(item -> {
            ResourceLocation itemLocation = BuiltInRegistries.ITEM.getKey(item);

            if (location.getNamespace().equals(itemLocation.getNamespace()) && location.getPath().endsWith(itemLocation.getPath())) {
                List<ItemOverride.Predicate> predicates = Lists.newArrayList();
                predicates.add(new ItemOverride.Predicate(new ResourceLocation(AllElytrasNeeded.MOD_ID, "elytra_container"), 1.0F));
                instance.getOverrides().add(new ItemOverride(new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/elytra_" + itemLocation.getPath()), predicates));
            }
        });



    }

}
