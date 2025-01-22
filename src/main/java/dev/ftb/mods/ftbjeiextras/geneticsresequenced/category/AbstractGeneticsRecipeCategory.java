package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category;

import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGeneticsRecipeCategory<T> implements IRecipeCategory<T> {

    protected final IGuiHelper guiHelper;
    private final IDrawable background;
    private final Component title;
    private final RecipeType<T> recipeType;

    public AbstractGeneticsRecipeCategory(IJeiHelpers helpers, ItemLike itemLike, String key, RecipeType<T> recipeType) {
        this.guiHelper = helpers.getGuiHelper();
        this.background = guiHelper.createDrawableItemStack(new ItemStack(itemLike));
        this.title = GeneticsHelpers.categoryName(key);
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

}
