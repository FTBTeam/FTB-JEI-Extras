package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.ConversionRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
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
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractConversionRecipeCategory<T extends ConversionRecipe> implements IRecipeCategory<T> {

    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final Component title;
    private final RecipeType<T> recipeType;

    public AbstractConversionRecipeCategory(IJeiHelpers helpers, ItemLike itemLike, String key, RecipeType<T> recipeType) {
        this.guiHelper = helpers.getGuiHelper();
        this.background = guiHelper.createDrawableItemStack(new ItemStack(itemLike));
        this.title = GeneticsHelpers.categoryName(key); //recipeType.getUid().getPath().replace("ga_", "")
        this.recipeType = recipeType;
    }

    @Override
    @Nullable
    public final IDrawable getIcon() {
        return background;
    }

    @Override
    public final Component getTitle() {
        return title;
    }

    @Override
    public final RecipeType<T> getRecipeType() {
        return recipeType;
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
                .addIngredients(recipe.getInput());
        builder.addOutputSlot(58, 0)
                .addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiHelper.getSlotDrawable().draw(guiGraphics, -1, -1);
        guiHelper.getSlotDrawable().draw(guiGraphics, 57, -1);
    }
}
