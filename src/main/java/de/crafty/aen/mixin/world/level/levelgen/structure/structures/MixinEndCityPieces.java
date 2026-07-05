package de.crafty.aen.mixin.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EndCityPieces.class)
public abstract class MixinEndCityPieces {


    @Inject(method = "recursiveChildren", at = @At("RETURN"))
    private static void addTieredElytraShips(StructureTemplateManager structureTemplateManager, EndCityPieces.SectionGenerator sectionGenerator, int i, EndCityPieces.EndCityPiece endCityPiece, BlockPos blockPos, List<StructurePiece> list, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {


        for (int j = 0; j < list.size(); j++) {
            if (!(list.get(j) instanceof EndCityPieces.EndCityPiece piece))
                continue;

            if (!"ship".equals(((TemplateStructurePieceAccessor) piece).getTemplateName()))
                continue;

            float f = randomSource.nextFloat();
            String shipVariant = "ship";
            if(f < 0.125F)
                shipVariant = "gold_ship";
            else if(f < 0.25F)
                shipVariant = "iron_ship";
            else if(f < 0.3F)
                shipVariant = "diamond_ship";

            list.set(j, new EndCityPieces.EndCityPiece(structureTemplateManager,  shipVariant, piece.templatePosition(), piece.getRotation(), true));

        }
    }

}
