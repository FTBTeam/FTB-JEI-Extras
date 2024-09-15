package dev.ftb.mods.ftbjeiextras.extendedae;

import appeng.core.AppEng;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
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
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class AssemblerRecipeCategory implements IRecipeCategory<RecipeHolder<CrystalAssemblerRecipe>> {
    public static final RecipeType<RecipeHolder<CrystalAssemblerRecipe>> TYPE = RecipeType.createFromVanilla(CrystalAssemblerRecipe.TYPE);
    private static final ResourceLocation BG_ASSET = AppEng.makeId("textures/guis/crystal_assembler.png");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawableAnimated charge;

    public AssemblerRecipeCategory(IJeiHelpers helpers) {
        var guiHelper = helpers.getGuiHelper();
        background = guiHelper.createDrawable(BG_ASSET, 23, 19, 135, 58);
        icon = guiHelper.createDrawableItemStack(new ItemStack(EAESingletons.CRYSTAL_ASSEMBLER));
        charge = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(BG_ASSET, 176, 0, 6, 18), 40, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<RecipeHolder<CrystalAssemblerRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.extendedae.category.assembler");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystalAssemblerRecipe> recipeHolder, IFocusGroup focuses) {
        var recipe = recipeHolder.value();

        // Logic / Layout copied from the original EMI plugin
        int x = 3;
        int y = 3;
        for (var in : recipe.getInputs()) {
            if (!in.isEmpty()) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(GlodiumHelpers.of(in));
                x += 18;
                if (x >= 18 * 3) {
                    y += 18;
                    x = 3;
                }
            }
        }

        if (recipe.getFluid() != null) {
            var fluid = recipe.getFluid();
            FluidStack[] stacks = fluid.getIngredient().getStacks();
            for (FluidStack stack : stacks) {
                if (stack.isEmpty()) {
                    continue;
                }

                builder.addSlot(RecipeIngredientRole.CATALYST, 58, 39).addFluidStack(stack.getFluid(), fluid.getStackAmount(stack));
                break;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 21).addItemStack(recipe.output);
    }

    @Override
    public void draw(RecipeHolder<CrystalAssemblerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.charge.draw(guiGraphics, 129, 20);
    }
}
