package dev.ftb.mods.ftbjeiextras.modspecific;

import com.glodblock.github.glodium.recipe.stack.IngredientStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.stream.Stream;

public class GlodiumHelpers {
    public static Ingredient of(IngredientStack.Item stack) {
        if (stack.isEmpty()) {
            return Ingredient.EMPTY;
        }

        Ingredient ingredient = stack.getIngredient();
        Stream<ItemStack> stacks = Arrays.stream(ingredient.getItems()).map(oldStack -> oldStack.copyWithCount(stack.getAmount()));

        return Ingredient.of(stacks);
    }
}
