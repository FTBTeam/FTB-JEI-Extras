package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PurifySyringeRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class BloodPurifierRecipeCategory extends AbstractConversionRecipeCategory<PurifySyringeRecipe> {

    public static final RecipeType<PurifySyringeRecipe> TYPE = GeneticsHelpers.createRecipeType("blood_purifier", PurifySyringeRecipe.class);

    public BloodPurifierRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getBLOOD_PURIFIER(), "blood_purifier", TYPE);
    }

}
