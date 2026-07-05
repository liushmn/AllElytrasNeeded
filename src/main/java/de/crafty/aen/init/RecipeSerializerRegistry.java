package de.crafty.aen.init;

import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.crafting.SmithingElytraMergeRecipe;
import de.crafty.aen.crafting.SmithingElytraUpgradeRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerRegistry {


    public static final RecipeSerializer<SmithingElytraUpgradeRecipe> ELYTRA_UPGRADE = register("elytra_upgrade", new SmithingElytraUpgradeRecipe.Serializer());
    public static final RecipeSerializer<SmithingElytraMergeRecipe> ELYTRA_MERGE = register("elytra_merge", new SmithingElytraMergeRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(AllElytrasNeeded.MOD_ID, id), recipeSerializer);
    }

    public static void perform(){

    }
}
