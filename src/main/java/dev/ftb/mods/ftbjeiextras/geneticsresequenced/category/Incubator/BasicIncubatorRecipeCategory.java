package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.BasicIncubatorRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class BasicIncubatorRecipeCategory extends AbstractIncubatorRecipe<BasicIncubatorRecipe> {

    public static final RecipeType<BasicIncubatorRecipe> TYPE = GeneticsHelpers.createRecipeType("incubator", BasicIncubatorRecipe.class);

    public BasicIncubatorRecipeCategory(IJeiHelpers helpers) {
        super(helpers, "incubator", TYPE);
    }
}
