package dev.ftb.mods.ftbjeiextras.advancedae;

import appeng.core.AppEng;
import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.modspecific.GlodiumHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.AdvancedAE;
import net.pedroksl.advanced_ae.common.definitions.AAEBlocks;
import net.pedroksl.advanced_ae.common.definitions.AAEText;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;
import org.jetbrains.annotations.Nullable;

public class ReactionChamberRecipeCategory implements IRecipeCategory<ReactionChamberRecipe> {
    public static final RecipeType<ReactionChamberRecipe> TYPE = RecipeType.create("advanced_ae", "reaction", ReactionChamberRecipe.class);

    private static final ResourceLocation BG_ASSET = FTBJeiExtras.id("textures/guis/reaction_chamber.png");
    private static final ResourceLocation BOLT_ASSET = AdvancedAE.makeId("textures/guis/emi.png"); // Bad name

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawableAnimated charge;

    private final IGuiHelper guiHelper;

    public ReactionChamberRecipeCategory(IJeiHelpers helpers) {
        guiHelper = helpers.getGuiHelper();
        background = guiHelper.createDrawable(BG_ASSET, 20, 22, 135, 58);
        icon = guiHelper.createDrawableItemStack(new ItemStack(AAEBlocks.REACTION_CHAMBER));
        charge = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(BG_ASSET, 176, 0, 6, 18), 40, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<ReactionChamberRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(AAEText.EmiReactionChamber.getTranslationKey());
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
    public void setRecipe(IRecipeLayoutBuilder builder, ReactionChamberRecipe recipe, IFocusGroup focuses) {
        int x = 12;
        for (var in : recipe.getInputs()) {
            if (in.isEmpty()) {
                continue;
            }

            builder.addSlot(RecipeIngredientRole.INPUT, x, 6).addIngredients(GlodiumHelpers.of(in));
            x += 18;
        }

        if (recipe.getFluid() != null) {
            var fluid = recipe.getFluid();
            FluidStack[] stacks = fluid.getIngredient().getStacks();
            for (FluidStack stack : stacks) {
                if (stack.isEmpty()) {
                    continue;
                }

                builder.addSlot(RecipeIngredientRole.CATALYST, 30, 25).addFluidStack(stack.getFluid(), fluid.getStackAmount(stack));
                break;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 100, 6).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ReactionChamberRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.charge.draw(guiGraphics, 122, 5);
        guiGraphics.drawString(Minecraft.getInstance().font, AAEText.ReactionChamberEnergy.text(recipe.getEnergy() / 1000), 18, 58 - 10, AAEText.TOOLTIP_DEFAULT_COLOR, false);

        guiHelper.drawableBuilder(BOLT_ASSET, 0, 0, 16, 16)
                .setTextureSize(32, 32)
                .build()
                .draw(guiGraphics, 4, 58 - 14);
    }
}
