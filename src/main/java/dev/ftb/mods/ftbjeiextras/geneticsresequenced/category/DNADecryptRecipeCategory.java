package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.DecryptHelixRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class DNADecryptRecipeCategory extends AbstractConversionRecipeCategory<DecryptHelixRecipe> {

    public static final RecipeType<DecryptHelixRecipe> TYPE = GeneticsHelpers.createRecipeType("dna_decryptor", DecryptHelixRecipe.class);

    public DNADecryptRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getDNA_DECRYPTOR(), "dna_decryptor", TYPE);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, DecryptHelixRecipe recipe, IFocusGroup focuses) {
        super.createRecipeExtras(builder, recipe, focuses);
        builder.addText(Component.literal(String.format("%.2f%%", recipe.getChance() * 100)), 32, 24)
                .setPosition(-38, 4);
    }
}
