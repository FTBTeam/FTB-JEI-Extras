package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public record BasicIncubatorRecipe(Ingredient ingredient, Ingredient input, ItemStack output) implements IncubatorRecipe {

    public static List<BasicIncubatorRecipe> collectAllRecipes(RecipeManager recipeManager) {
        return dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.BasicIncubatorRecipe.Companion.getBasicRecipes(recipeManager).stream()
                .map(recipe -> {
                    dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.BasicIncubatorRecipe value = recipe.value();
                    return new BasicIncubatorRecipe(value.getTopIngredient(), value.getBottomIngredient(), value.getOutputStack());
                }).toList();
    }
}
