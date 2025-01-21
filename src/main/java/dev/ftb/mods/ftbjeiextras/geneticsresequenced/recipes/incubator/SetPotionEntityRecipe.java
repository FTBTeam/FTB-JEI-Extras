package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.recipe.BrewingRecipes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetPotionEntityRecipe implements IncubatorRecipe {

    private final Ingredient ingredient;
    private final Ingredient input;
    private final ItemStack output;

    public SetPotionEntityRecipe(EntityType<?> entityType, boolean mutation) {
        ItemStack cell = ModItems.INSTANCE.getCELL().toStack();
        EntityDnaItem.Companion.setEntityType(cell, entityType);
        this.ingredient = Ingredient.of(cell);

        ItemStack potion = mutation ? BrewingRecipes.INSTANCE.getMutationPotionStack() : BrewingRecipes.INSTANCE.getCellGrowthPotionStack();
        this.input = Ingredient.of(potion);

        EntityDnaItem.Companion.setEntityType(potion, entityType);
        this.output = potion;
    }

    @Override
    public Ingredient ingredient() {
        return ingredient;
    }

    @Override
    public Ingredient input() {
        return input;
    }

    @Override
    public ItemStack output() {
        return output;
    }

    public static List<SetPotionEntityRecipe> collectAllRecipes(RecipeManager recipeManager) {
        List<SetPotionEntityRecipe> recipes = new ArrayList<>();
        Set<EntityType<?>> validEntityTypes = EntityDnaItem.Companion.getValidEntityTypes();
        for (EntityType<?> validEntityType : validEntityTypes) {
            recipes.add(new SetPotionEntityRecipe(validEntityType, false));
            recipes.add(new SetPotionEntityRecipe(validEntityType, true));
        }
        return recipes;
    }
}
