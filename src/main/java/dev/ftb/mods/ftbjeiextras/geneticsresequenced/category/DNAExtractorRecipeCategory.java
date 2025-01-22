package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.CellToHelixRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.OrganicMatterRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class DNAExtractorRecipeCategory extends AbstractConversionRecipeCategory<CellToHelixRecipe> {

    public static final RecipeType<CellToHelixRecipe> TYPE = GeneticsHelpers.createRecipeType("dna_extractor", CellToHelixRecipe.class);

    public DNAExtractorRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getDNA_EXTRACTOR(), "dna_extractor", TYPE);
    }
}
