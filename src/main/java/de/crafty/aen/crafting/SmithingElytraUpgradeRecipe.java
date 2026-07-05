package de.crafty.aen.crafting;

import com.google.gson.JsonObject;
import de.crafty.aen.elytra.ElytraComponent;
import de.crafty.aen.init.RecipeSerializerRegistry;
import de.crafty.aen.items.TieredElytraItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.net.TcpSocketManager;
import org.jetbrains.annotations.NotNull;

public class SmithingElytraUpgradeRecipe implements SmithingRecipe {

    private final ResourceLocation id;
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;

    public SmithingElytraUpgradeRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition) {
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
        if(!TieredElytraItem.containsElytraComponent(container.getItem(1)))
            return false;

        ElytraComponent component = TieredElytraItem.getOrCreateElytraComponent(container.getItem(1));
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2))
                && component.canApplyEffect(container.getItem(2));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseStack = container.getItem(1);
        ItemStack stack = baseStack.copy();

        ElytraComponent component = TieredElytraItem.getOrCreateElytraComponent(stack);
        component.addEffectSlot(container.getItem(2));

        return stack;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(Items.ELYTRA);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.ELYTRA_UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<SmithingElytraUpgradeRecipe> {


        @Override
        public @NotNull SmithingElytraUpgradeRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "template"));
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "addition"));
            return new SmithingElytraUpgradeRecipe(resourceLocation, template, base, addition);
        }

        @Override
        public @NotNull SmithingElytraUpgradeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient template = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient addition = Ingredient.fromNetwork(friendlyByteBuf);
            return new SmithingElytraUpgradeRecipe(resourceLocation, template, base, addition);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, SmithingElytraUpgradeRecipe recipe) {
            recipe.template.toNetwork(friendlyByteBuf);
            recipe.base.toNetwork(friendlyByteBuf);
            recipe.addition.toNetwork(friendlyByteBuf);
        }
    }
}
