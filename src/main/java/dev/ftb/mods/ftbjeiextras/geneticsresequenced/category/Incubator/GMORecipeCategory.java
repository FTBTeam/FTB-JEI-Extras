package dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator;

import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.AbstractGeneticsRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.GMORecipe;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class GMORecipeCategory extends AbstractGeneticsRecipeCategory<GMORecipe> {

    public static final RecipeType<GMORecipe> TYPE = GeneticsHelpers.createRecipeType("gmo", GMORecipe.class);

    public GMORecipeCategory(IJeiHelpers helpers) {
        super(helpers, ModItems.INSTANCE.getGMO_CELL(), "gmo", TYPE);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GMORecipe recipe, IFocusGroup focuses) {
        int displayHeight = 2 + 18 + 2 + 18 + 2;
        int buffer = 5;
        int slotSize = 18;
        int x = 0;

        x+= 5;

        builder.addInputSlot(x, displayHeight / 4)
                .addIngredients(recipe.getInput());
        x += slotSize + buffer;

        builder.addInputSlot(x, displayHeight / 4)
                .addIngredients(recipe.getIngredient());
        x += guiHelper.getRecipeArrow().getWidth() * 2 + 4;

        builder.addOutputSlot(x, 2)
                .addItemStack(recipe.getGoodOutput());

        builder.addOutputSlot(x, 2 + 18 + 2)
                .addItemStack(recipe.getBadOutput());

    }

    @Override
    public void draw(GMORecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 4, 9);
        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 27, 9);
        guiHelper.getRecipeArrow()
                .draw(guiGraphics, 49, 9);
        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 75, 1);
        guiHelper.getSlotDrawable()
                .draw(guiGraphics, 75, 21);

        float successChance = recipe.getChance() * 100;
        float failureChance = 100 - successChance;
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(String.format("Success: %.1f", successChance) + "%"), 96, 6, ChatFormatting.GREEN.getColor());
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(String.format("Failure: %.1f", failureChance) + "%"), 97, 26, ChatFormatting.RED.getColor());
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, GMORecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);
        tooltip.add(Component.translatable(ModLanguageProvider.Tooltips.GMO_TEMPERATURE_REQUIREMENT).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable(ModLanguageProvider.Tooltips.GMO_CHORUS).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getWidth() {
        return 192 - 18;
    }

    @Override
    public int getHeight() {
        return  2 + 18 + 2 + 18 + 2;
    }
}
