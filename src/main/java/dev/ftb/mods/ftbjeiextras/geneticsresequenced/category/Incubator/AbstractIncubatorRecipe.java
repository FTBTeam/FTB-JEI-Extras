package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.AbstractGeneticsRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.IncubatorRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractIncubatorRecipe<T extends IncubatorRecipe> extends AbstractGeneticsRecipeCategory<T> {

    private static final int WIDTH = 75;
    private static final int HEIGHT = 61;
    private static final ResourceLocation BACKGROUND = GeneticsHelpers.rl("textures/gui/container/incubator_emi.png");

    private final IDrawableStatic background;

    public AbstractIncubatorRecipe(IJeiHelpers helpers, String key, RecipeType<T> recipeType) {
        super(helpers, ModBlocks.INSTANCE.getINCUBATOR(), key, recipeType);
        this.background = helpers.getGuiHelper().createDrawable(BACKGROUND, 55, 14, 65, 61);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 5, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addOutputSlot(6, 37)
                .addIngredients(recipe.input());
        builder.addInputSlot(29, 3)
                .addIngredients(recipe.ingredient());
        builder.addOutputSlot(52, 37)
                .addItemStack(recipe.output());
    }

}
