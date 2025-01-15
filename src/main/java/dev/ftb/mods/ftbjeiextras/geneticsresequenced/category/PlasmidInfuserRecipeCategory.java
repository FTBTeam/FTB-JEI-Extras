package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInfuserRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class PlasmidInfuserRecipeCategory extends AbstractConversionRecipeCategory<PlasmidInfuserRecipe> {

    public static final RecipeType<PlasmidInfuserRecipe> TYPE = GeneticsHelpers.createRecipeType("plasmid_infuser", PlasmidInfuserRecipe.class);

    public PlasmidInfuserRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getPLASMID_INFUSER(), "plasmid_infuser", TYPE);
    }

}
