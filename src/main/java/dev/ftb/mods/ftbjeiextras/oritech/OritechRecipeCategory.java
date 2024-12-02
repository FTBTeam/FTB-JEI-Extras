package dev.ftb.mods.ftbjeiextras.oritech;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.fluid.FluidStack;
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
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.base.entity.MachineBlockEntity;
import rearth.oritech.block.base.entity.UpgradableGeneratorBlockEntity;
import rearth.oritech.client.ui.BasicMachineScreen;
import rearth.oritech.init.recipes.OritechRecipe;
import rearth.oritech.util.InventorySlotAssignment;
import rearth.oritech.util.ScreenProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class OritechRecipeCategory implements IRecipeCategory<RecipeHolder<OritechRecipe>> {

    public static final ResourceLocation GUI_COMPONENTS = BasicMachineScreen.GUI_COMPONENTS;

    // Todo don't depend on JEI
    private static final ResourceLocation jei = ResourceLocation.fromNamespaceAndPath("jei", "textures/jei/gui/gui_vanilla.png");

    private final int offsetX = 23;
    private final int offsetY = 17;

    private final MachineBlockEntity screenProvider;
    private final RecipeType<RecipeHolder<OritechRecipe>> recipeType;
    private final IDrawable icon;
    private final IDrawable background;
    private final IGuiHelper guiHelper;
    private final IDrawableStatic staticFlame;
    private final Component title;

    private final List<ScreenProvider.GuiSlot> slots;
    private final InventorySlotAssignment slotOffsets;


    public OritechRecipeCategory(IJeiHelpers helpers, RecipeType<RecipeHolder<OritechRecipe>> recipeType, Class<? extends MachineBlockEntity> screenProviderSource, ItemLike machine) {
        this.guiHelper = helpers.getGuiHelper();
        this.recipeType = recipeType;
        this.background = guiHelper.createBlankDrawable(150, 66);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(machine));
        this.staticFlame = guiHelper.createDrawable(jei, 82, 114, 14, 14);
        this.title = Component.translatable("emi.category.oritech." + recipeType.getUid().getPath());

        var blockState = Blocks.STONE.defaultBlockState();
        if (machine instanceof Block blockItem)
            blockState = blockItem.defaultBlockState();
        var finalBlockState = blockState;

        try {
            this.screenProvider = screenProviderSource.getDeclaredConstructor(BlockPos.class, BlockState.class).newInstance(new BlockPos(0, 0, 0), finalBlockState);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.slots = screenProvider.getGuiSlots();
        this.slotOffsets = screenProvider.getSlots();

    }

    @Override
    public RecipeType<RecipeHolder<OritechRecipe>> getRecipeType() {
        return recipeType;
    }

    @Override
    public Component getTitle() {
        return title;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<OritechRecipe> recipeHolder, IFocusGroup focuses) {
        OritechRecipe recipe = recipeHolder.value();

        List<Ingredient> inputs = recipe.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            var input = inputs.get(i);
            if (input.isEmpty()) continue;

            var pos = slots.get(slotOffsets.inputStart() + i);
            builder.addSlot(RecipeIngredientRole.INPUT, pos.x() - offsetX, pos.y() - offsetY)
                    .addIngredients(input);
        }

        List<ItemStack> outputs = recipe.getResults();
        for (int i = 0; i < outputs.size(); i++) {
            var output = outputs.get(i);
            if (output.isEmpty()) continue;

            var pos = slots.get(slotOffsets.outputStart() + i);
            builder.addSlot(RecipeIngredientRole.OUTPUT, pos.x() - offsetX, pos.y() - offsetY)
                    .addItemStack(output);
        }

    }

    @Override
    public void draw(RecipeHolder<OritechRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        OritechRecipe recipe = recipeHolder.value();

        boolean isGenerating = screenProvider instanceof UpgradableGeneratorBlockEntity;


        // Todo how to make an animated flame / arrow?
        if (isGenerating) {
            staticFlame.draw(guiGraphics, 76 - offsetX, 41 - offsetY);
        } else  {
            guiHelper.drawableBuilder(jei, 82, 128, 24, 17)
                    .build()
                    .draw(guiGraphics, 80 - offsetX, 41 - offsetY);
        }

        List<Ingredient> inputs = recipe.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            var input = inputs.get(i);
            if (input.isEmpty()) continue;

            var pos = slots.get(slotOffsets.inputStart() + i);
            guiHelper.getSlotDrawable()
                    .draw(guiGraphics, pos.x() - offsetX - 1, pos.y() - offsetY - 1);
        }

        FluidStack fluidInput = recipe.getFluidInput();
        if (fluidInput != null && fluidInput.getAmount() > 0) {
            drawFluid(guiGraphics, fluidInput, 10, 6, 16, 50, mouseX, mouseY);
        }

        FluidStack fluidOutput = recipe.getFluidOutput();

        if (fluidOutput != null && fluidOutput.getAmount() > 0) {
            drawFluid(guiGraphics, fluidOutput, 120, 6, 16, 50, mouseX, mouseY);
        }

        List<ItemStack> outputs = recipe.getResults();
        for (int i = 0; i < outputs.size(); i++) {
            var output = outputs.get(i);
            if (output.isEmpty()) continue;

            var pos = slots.get(slotOffsets.outputStart() + i);
            guiHelper.getSlotDrawable()
                    .draw(guiGraphics, pos.x() - offsetX - 1, pos.y() - offsetY - 1);
        }

        var duration = String.format("%.0f", recipe.getTime() / 20f);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("emi.title.oritech.cookingtime", duration, recipe.getTime()), (int) (150 * 0.35), (int) (66 * 0.88), 0xFFFFFF, true);
    }

    // Todo improve this try to use addFluidI
    private void drawFluid(GuiGraphics guiGraphics, FluidStack fluidInput, int fluidX, int fluidY, int fluidWidth, int fluidMaxHeight, double mouseX, double mouseY) {
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidInput.getFluid().getFluidType());
        ResourceLocation FLUID_TEXTURE = props.getStillTexture().withPrefix("textures/").withSuffix(".png");
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int color = IClientFluidTypeExtensions.of(fluidInput.getFluid()).getTintColor();
        RenderSystem.setShaderColor((color >> 16 & 0xff) / 255f, (color >> 8 & 0xff) / 255f, (color & 0xff) / 255f, 0.9f);

        guiHelper.drawableBuilder(FLUID_TEXTURE, 0, 0, 16, 50)
                .setTextureSize(16, 16)
                .build()
                .draw(guiGraphics, 10, 6);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();

        guiHelper.drawableBuilder(GUI_COMPONENTS, 48, 0, fluidWidth, fluidMaxHeight)
                .setTextureSize(98, 96)
                .build()
                .draw(guiGraphics, fluidX, fluidY);

        if (mouseX >= fluidX && mouseX <= fluidX + fluidWidth && mouseY >= fluidY && mouseY <= fluidY + fluidMaxHeight) {
            List<Component> textComponents = List.of(
                    Component.literal("Fluid: ").append(Component.translatable(fluidInput.getFluid().getFluidType().getDescriptionId())),
                    Component.literal("Amount: " + fluidInput.getAmount())
            );
            guiGraphics.renderTooltip(Minecraft.getInstance().font, textComponents, Optional.empty(), (int) mouseX, (int) mouseY);
        }
    }
}
