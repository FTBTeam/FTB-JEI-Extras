package dev.ftb.mods.ftbjeiextras.advancedae;

import appeng.core.AppEng;
import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.AdvancedAE;
import net.pedroksl.advanced_ae.common.definitions.AAEBlocks;
import net.pedroksl.advanced_ae.common.definitions.AAEText;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ReactionChamberRecipeCategory implements IRecipeCategory<ReactionChamberRecipe> {
    public static final RecipeType<ReactionChamberRecipe> TYPE = RecipeType.create("advanced_ae", "reaction", ReactionChamberRecipe.class);

    private static final ResourceLocation BG_ASSET = AppEng.makeId("textures/guis/reaction_chamber.png");
    private static final ResourceLocation BOLT_ASSET = AdvancedAE.makeId("textures/guis/emi.png"); // Bad name

    private IDrawableStatic background;
    private IDrawable icon;
    private IDrawableAnimated charge;
    private IDrawableStatic boltIcon;

    private final IGuiHelper guiHelper;

    public ReactionChamberRecipeCategory(IJeiHelpers helpers) {
        guiHelper = helpers.getGuiHelper();
        background = guiHelper.createDrawable(BG_ASSET, 5, 15, 168, 75);
        icon = guiHelper.createDrawableItemStack(new ItemStack(AAEBlocks.REACTION_CHAMBER));
        charge = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(BG_ASSET, 176, 0, 6, 18), 40, IDrawableAnimated.StartDirection.BOTTOM, false);

        boltIcon = guiHelper.createDrawable(BOLT_ASSET, 0, 0, 16, 16);
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
        int x = 37;
        for (var in : recipe.getInputs()) {
            if (in.isEmpty()) {
                continue;
            }

            builder.addSlot(RecipeIngredientRole.INPUT, x, 9).addIngredients(GlodiumHelpers.of(in));
            x += 18;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 28).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ReactionChamberRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.charge.draw(guiGraphics, 135, 27);
        guiGraphics.drawString(Minecraft.getInstance().font, AAEText.ReactionChamberEnergy.text(recipe.getEnergy() / 1000), 35, 68, AAEText.TOOLTIP_DEFAULT_COLOR, false);

        guiHelper.drawableBuilder(BOLT_ASSET, 0, 0, 16, 16)
                .setTextureSize(32, 32)
                .build()
                .draw(guiGraphics, 22, 65);

        int fluidX = 4;
        int fluidY = 6;
        int fluidWidth = 16;
        int fluidMaxHeight = 58;
        int maxAmount = 16000;

        if (recipe.getFluid() != null) {
            var fluid = recipe.getFluid();
            FluidStack[] stacks = fluid.getIngredient().getStacks();

            for (FluidStack stack : stacks) {
                if (stack.isEmpty()) continue;

                IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(stack.getFluidType());
                ResourceLocation FLUID_TEXTURE = props.getStillTexture().withPrefix("textures/").withSuffix(".png");
                RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                int color = IClientFluidTypeExtensions.of(stack.getFluid()).getTintColor();
                RenderSystem.setShaderColor((color >> 16 & 0xff) / 255f, (color >> 8 & 0xff) / 255f, (color & 0xff) / 255f, 0.9f);

                int fluidAmount = stack.getAmount();
                int fluidHeight = (int) ((fluidAmount / (float) maxAmount) * fluidMaxHeight * 2);
                int renderY = fluidY + fluidMaxHeight - fluidHeight;

                guiHelper.drawableBuilder(FLUID_TEXTURE, 0, 0, fluidWidth, fluidHeight)
                        .setTextureSize(16, 16)
                        .build()
                        .draw(guiGraphics, fluidX, renderY);

                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();

                if (mouseX >= fluidX && mouseX <= fluidX + fluidWidth && mouseY >= fluidY && mouseY <= fluidY + fluidMaxHeight) {
                    List<Component> textComponents = List.of(
                            Component.literal("Fluid: ").append(Component.translatable(stack.getFluidType().getDescriptionId())),
                            Component.literal("Amount: " + stack.getAmount())
                    );
                    guiGraphics.renderTooltip(Minecraft.getInstance().font, textComponents, Optional.empty(), (int) mouseX, (int) mouseY);
                }
                break;
            }
        }

    }
}
