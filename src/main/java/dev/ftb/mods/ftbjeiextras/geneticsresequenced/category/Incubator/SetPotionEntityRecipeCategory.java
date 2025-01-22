package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.SetPotionEntityRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;

public class SetPotionEntityRecipeCategory extends AbstractIncubatorRecipe<SetPotionEntityRecipe> {

    public static final RecipeType<SetPotionEntityRecipe> TYPE = GeneticsHelpers.createRecipeType("set_entity", SetPotionEntityRecipe.class);

    public SetPotionEntityRecipeCategory(IJeiHelpers helpers) {
        super(helpers, "set_entity", TYPE);
    }
}