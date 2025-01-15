package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface ConversionRecipe {
    Ingredient getInput();

    ItemStack getOutput();
}
