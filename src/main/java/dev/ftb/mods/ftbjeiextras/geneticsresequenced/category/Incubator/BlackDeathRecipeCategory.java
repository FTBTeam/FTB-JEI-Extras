package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.BlackDeathRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.SetPotionEntityRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class BlackDeathRecipeCategory extends AbstractIncubatorRecipe<BlackDeathRecipe> {

    public static final RecipeType<BlackDeathRecipe> TYPE = GeneticsHelpers.createRecipeType("black_death", BlackDeathRecipe.class);

    public BlackDeathRecipeCategory(IJeiHelpers helpers) {
        super(helpers, "incubator", TYPE);
    }
}