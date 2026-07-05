package de.crafty.aen.mixin.world.level.levelgen.structure.structures;

import de.crafty.aen.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCityPieces.EndCityPiece.class)
public abstract class MixinEndCityPiece extends TemplateStructurePiece {


    public MixinEndCityPiece(StructurePieceType structurePieceType, int i, StructureTemplateManager structureTemplateManager, ResourceLocation resourceLocation, String string, StructurePlaceSettings structurePlaceSettings, BlockPos blockPos) {
        super(structurePieceType, i, structureTemplateManager, resourceLocation, string, structurePlaceSettings, blockPos);
    }

    @Inject(method = "handleDataMarker", at = @At("RETURN"))
    private void spawnTieredElytras(String string, BlockPos blockPos, ServerLevelAccessor serverLevelAccessor, RandomSource randomSource, BoundingBox boundingBox, CallbackInfo ci){
        if(string.startsWith("IronElytra")){
            ItemFrame itemFrame = new ItemFrame(serverLevelAccessor.getLevel(), blockPos, this.placeSettings.getRotation().rotate(Direction.SOUTH));
            itemFrame.setItem(new ItemStack(ItemRegistry.IRON_ELYTRA), false);
            serverLevelAccessor.addFreshEntity(itemFrame);
        }
        if(string.startsWith("DiamondElytra")){
            ItemFrame itemFrame = new ItemFrame(serverLevelAccessor.getLevel(), blockPos, this.placeSettings.getRotation().rotate(Direction.SOUTH));
            itemFrame.setItem(new ItemStack(ItemRegistry.IRON_ELYTRA), false);
            serverLevelAccessor.addFreshEntity(itemFrame);
        }
        if(string.startsWith("GoldElytra")){
            ItemFrame itemFrame = new ItemFrame(serverLevelAccessor.getLevel(), blockPos, this.placeSettings.getRotation().rotate(Direction.SOUTH));
            itemFrame.setItem(new ItemStack(ItemRegistry.IRON_ELYTRA), false);
            serverLevelAccessor.addFreshEntity(itemFrame);
        }
    }
}
