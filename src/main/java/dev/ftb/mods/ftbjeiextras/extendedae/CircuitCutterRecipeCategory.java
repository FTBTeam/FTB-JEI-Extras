package dev.ftb.mods.ftbjeiextras.extendedae;

import appeng.core.AppEng;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GlodiumHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class CircuitCutterRecipeCategory implements IRecipeCategory<RecipeHolder<CircuitCutterRecipe>> {
    public static final RecipeType<RecipeHolder<CircuitCutterRecipe>> TYPE = RecipeType.createFromVanilla(CircuitCutterRecipe.TYPE);
    private static final ResourceLocation BG_ASSET = AppEng.makeId("textures/guis/circuit_cutter.png");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawableAnimated charge;

    public CircuitCutterRecipeCategory(IJeiHelpers helpers) {
        var guiHelper = helpers.getGuiHelper();
        background = guiHelper.createDrawable(BG_ASSET, 43, 32, 94, 26);
        icon = guiHelper.createDrawableItemStack(new ItemStack(EAESingletons.CIRCUIT_CUTTER));
        charge = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(BG_ASSET, 176, 0, 6, 18), 40, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<RecipeHolder<CircuitCutterRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.extendedae.category.cutter");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CircuitCutterRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 5).addIngredients(GlodiumHelpers.of(recipe.value().getInput()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 5).addItemStack(recipe.value().output);
    }

    @Override
    public void draw(RecipeHolder<CircuitCutterRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        charge.draw(guiGraphics, 88, 4);
    }
}
