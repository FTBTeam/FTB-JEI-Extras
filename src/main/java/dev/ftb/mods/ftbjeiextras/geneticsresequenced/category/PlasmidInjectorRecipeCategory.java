package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.ConversionRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInfuserRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInjectorRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PlasmidInjectorRecipeCategory extends AbstractGeneticsRecipeCategory<PlasmidInjectorRecipe> {

    public static final RecipeType<PlasmidInjectorRecipe> TYPE = GeneticsHelpers.createRecipeType("plasmid_injector", PlasmidInjectorRecipe.class);

    public PlasmidInjectorRecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModBlocks.INSTANCE.getPLASMID_INJECTOR(), "plasmid_injector", TYPE);
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth() {
        return 102;
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, PlasmidInjectorRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow()
                .setPosition(48, 1);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PlasmidInjectorRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(0, 0)
                .addIngredients(recipe.getPlasmid());
        builder.addOutputSlot(24, 0)
                .addIngredients(recipe.getSyringeBefore());
        builder.addOutputSlot(78, 0)
                .addItemStack(recipe.getSyringeAfter());
    }

    @Override
    public void draw(PlasmidInjectorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiHelper.getSlotDrawable().draw(guiGraphics, -1, -1);
        guiHelper.getSlotDrawable().draw(guiGraphics, 77, -1);
        guiHelper.getSlotDrawable().draw(guiGraphics, 23, -1);
    }
}
