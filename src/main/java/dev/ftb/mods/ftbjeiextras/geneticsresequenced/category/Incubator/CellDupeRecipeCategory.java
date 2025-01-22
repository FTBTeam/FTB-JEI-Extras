package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.CellDupeRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class CellDupeRecipeCategory extends AbstractIncubatorRecipe<CellDupeRecipe> {

    public static final RecipeType<CellDupeRecipe> TYPE = GeneticsHelpers.createRecipeType("cell_dupe", CellDupeRecipe.class);

    public CellDupeRecipeCategory(IJeiHelpers helpers) {
        super(helpers, "cell_dupe", TYPE);
    }
}
