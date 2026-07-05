package de.crafty.aen.crafting;

import com.google.gson.JsonObject;
import de.crafty.aen.init.RecipeSerializerRegistry;
import de.crafty.aen.util.ElytraArmorHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SmithingElytraMergeRecipe implements SmithingRecipe {

    private final ResourceLocation id;
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;

    public SmithingElytraMergeRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack itemStack) {
        return this.template.test(itemStack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack itemStack) {
        return this.base.test(itemStack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack itemStack) {
        return this.addition.test(itemStack);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2))
                && ElytraArmorHelper.canMerge(container.getItem(2), container.getItem(1));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack chestplateStack = container.getItem(1);
        ItemStack stack = chestplateStack.copy();
        ElytraArmorHelper.merge(container.getItem(2), stack);
        return stack;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(Items.IRON_CHESTPLATE);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.ELYTRA_MERGE;
    }

    public static class Serializer implements RecipeSerializer<SmithingElytraMergeRecipe> {


        @Override
        public @NotNull SmithingElytraMergeRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "template"));
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "addition"));
            return new SmithingElytraMergeRecipe(resourceLocation, template, base, addition);
        }

        @Override
        public @NotNull SmithingElytraMergeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient template = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient addition = Ingredient.fromNetwork(friendlyByteBuf);
            return new SmithingElytraMergeRecipe(resourceLocation, template, base, addition);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, SmithingElytraMergeRecipe recipe) {
            recipe.template.toNetwork(friendlyByteBuf);
            recipe.base.toNetwork(friendlyByteBuf);
            recipe.addition.toNetwork(friendlyByteBuf);
        }
    }
}
