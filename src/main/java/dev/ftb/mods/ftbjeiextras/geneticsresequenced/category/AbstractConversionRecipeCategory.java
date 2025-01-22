package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.ConversionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.ItemLike;

public abstract class AbstractConversionRecipeCategory<T extends ConversionRecipe> extends AbstractGeneticsRecipeCategory<T> {

    public AbstractConversionRecipeCategory(IJeiHelpers helpers, ItemLike itemLike, String key, RecipeType<T> recipeType) {
        super(helpers, itemLike, key, recipeType);
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth() {
        return 76;
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addRecipeArrow()
                .setPosition(26, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addInputSlot(0, 0)
                .addIngredients(recipe.input());
        builder.addOutputSlot(58, 0)
                .addItemStack(recipe.output());
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiHelper.getSlotDrawable().draw(guiGraphics, -1, -1);
        guiHelper.getSlotDrawable().draw(guiGraphics, 57, -1);
    }
}
