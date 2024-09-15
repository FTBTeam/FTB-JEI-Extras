package dev.ftb.mods.ftbjeiextras.extendedae;

import appeng.core.AppEng;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CrystalFixerRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbjeiextras.modspecific.GlodiumHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CrystalFixerRecipeCategory implements IRecipeCategory<RecipeHolder<CrystalFixerRecipe>> {
    public static final RecipeType<RecipeHolder<CrystalFixerRecipe>> TYPE = RecipeType.createFromVanilla(CrystalFixerRecipe.TYPE);
    private static final ResourceLocation BG_ASSET = AppEng.makeId("textures/xei/crystal_fixer.png");

    private static final DecimalFormat FORMAT = new DecimalFormat("#.#%", new DecimalFormatSymbols());

    private final IDrawableStatic background;
    private final IDrawable icon;

    public CrystalFixerRecipeCategory(IJeiHelpers helpers) {
        var guiHelper = helpers.getGuiHelper();
        background = guiHelper.createDrawable(BG_ASSET, 0, 0, 114, 63);
        icon = guiHelper.createDrawableItemStack(new ItemStack(EAESingletons.CRYSTAL_FIXER));
    }

    @Override
    public RecipeType<RecipeHolder<CrystalFixerRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.extendedae.category.fixer");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystalFixerRecipe> recipeHolder, IFocusGroup focuses) {
        var recipe = recipeHolder.value();

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).addItemStack(new ItemStack(recipe.getInput()));
        builder.addSlot(RecipeIngredientRole.CATALYST, 49, 11).addIngredients(GlodiumHelpers.of(recipe.getFuel()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 19).addItemStack(new ItemStack(recipe.getOutput()));
    }

    @Override
    public void draw(RecipeHolder<CrystalFixerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("emi.extendedae.text.success_chance", FORMAT.format(recipe.value().getChance())), 1, 2, 0x7E7E7E, false);

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(41, 29, 0);
        pose.scale(2f, 2f, 1f);
        guiGraphics.renderItem(new ItemStack(EAESingletons.CRYSTAL_FIXER), 0, 0);
        pose.popPose();
    }
}
