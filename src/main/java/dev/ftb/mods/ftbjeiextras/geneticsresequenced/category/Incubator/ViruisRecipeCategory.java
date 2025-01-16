package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.VirusRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class ViruisRecipeCategory extends AbstractIncubatorRecipe<VirusRecipe> {

    public static final RecipeType<VirusRecipe> TYPE = GeneticsHelpers.createRecipeType("virus", VirusRecipe.class);

    public ViruisRecipeCategory(IJeiHelpers helpers) {
        super(helpers, "virus", TYPE);
    }
}