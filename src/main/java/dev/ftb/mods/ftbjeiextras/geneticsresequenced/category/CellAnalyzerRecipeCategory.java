package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.OrganicMatterRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CellAnalyzerRecipeCategory extends AbstractConversionRecipeCategory<OrganicMatterRecipe> {

    public static final RecipeType<OrganicMatterRecipe> TYPE = GeneticsHelpers.createRecipeType("cell_analyzer", OrganicMatterRecipe.class);

    public CellAnalyzerRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getCELL_ANALYZER(), "cell_analyzer", TYPE);
    }
}
