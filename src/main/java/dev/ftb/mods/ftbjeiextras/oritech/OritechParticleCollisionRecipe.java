package dev.ftb.mods.ftbjeiextras.oritech;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.recipes.OritechRecipe;

public class OritechParticleCollisionRecipe implements IRecipeCategory<RecipeHolder<OritechRecipe>> {

    private static final ResourceLocation PARTICLE = ResourceLocation.fromNamespaceAndPath("oritech", "textures/gui/modular/particle_recipe_overlay.png");

    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    private final RecipeType<RecipeHolder<OritechRecipe>> recipeType;

    public OritechParticleCollisionRecipe(IJeiHelpers helpers, RecipeType<RecipeHolder<OritechRecipe>> recipeType) {
        this.guiHelper = helpers.getGuiHelper();
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BlockContent.ACCELERATOR_CONTROLLER));
        this.background = guiHelper.createBlankDrawable(150, 66);
        this.recipeType = recipeType;
        this.title = Component.translatable("emi.category.oritech." + recipeType.getUid().getPath());
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

        builder.addSlot(RecipeIngredientRole.INPUT, 42, 20)
                .addIngredients(recipe.getInputs().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 20)
                .addIngredients(recipe.getInputs().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 69, 20)
                .addItemStack(recipe.getResults().get(0));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<OritechRecipe> recipeHolder, IFocusGroup focuses) {
        OritechRecipe recipe = recipeHolder.value();

        builder.addText(Component.translatable("emi.title.oritech.collisionspeed", recipe.getTime()), getWidth(), getHeight())
                .setShadow(true)
                .setTextAlignment(HorizontalAlignment.CENTER)
                .setColor(0xFFFFFF)
                .setPosition(0, getHeight() - Minecraft.getInstance().font.lineHeight * 2);
    }

    @Override
    public void draw(RecipeHolder<OritechRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        // Todo this is not working  https://github.com/Rearth/Oritech/blob/f8062dc79fb1e8a13126fd48f40989d6f89e66ef/common/src/main/java/rearth/oritech/init/compat/emi/OritechEMIParticleCollisionRecipe.java#L31

//        guiGraphics.blitSprite(PARTICLE, 60, 17, 16, 16);
        guiHelper.createDrawable(PARTICLE, 0, 0, 36, 24)
                .draw(guiGraphics, 0, 0);

        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 41, 19);

        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 95, 19);

    }
}
