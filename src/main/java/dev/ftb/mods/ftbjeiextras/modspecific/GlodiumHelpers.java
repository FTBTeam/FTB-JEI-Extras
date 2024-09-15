package dev.ftb.mods.ftbjeiextras.modspecific;

import com.glodblock.github.glodium.recipe.stack.IngredientStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class GlodiumHelpers {
    public static Ingredient of(IngredientStack.Item stack) {
        if (stack.isEmpty()) {
            return Ingredient.EMPTY;
        }

        Ingredient ingredient = stack.getIngredient();
        ItemStack[] items = ingredient.getItems();
        for (ItemStack item : items) {
            item.setCount(stack.getAmount());
        }

        return Ingredient.of(items);
    }
}
