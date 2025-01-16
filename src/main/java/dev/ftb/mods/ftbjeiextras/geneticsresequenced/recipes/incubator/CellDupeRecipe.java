package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.item.GmoCell;
import dev.aaronhowser.mods.geneticsresequenced.recipe.BrewingRecipes;
import dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.GmoRecipe;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CellDupeRecipe implements IncubatorRecipe {

    private final Ingredient input;
    private final ItemStack output;
    private final Ingredient ingredient;

    public CellDupeRecipe(ItemStack cellStack) {
        this.input = Ingredient.of(BrewingRecipes.INSTANCE.getSubstratePotionStack());
        this.output = cellStack.copy();
        this.ingredient = Ingredient.of(cellStack);
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


    public static List<CellDupeRecipe> collectAllRecipes(RecipeManager recipeManager, RegistryAccess registryAccess) {
        List<CellDupeRecipe> recipes = new ArrayList<>();

        Set<EntityType<?>> validEntityTypes = EntityDnaItem.Companion.getValidEntityTypes();

        for (EntityType<?> entityType : validEntityTypes) {
            ItemStack stack = ModItems.INSTANCE.getCELL().toStack();
            EntityDnaItem.Companion.setEntityType(stack, entityType);
            recipes.add(new CellDupeRecipe(stack));
        }

        List<RecipeHolder<GmoRecipe>> allGmoRecipes = GmoRecipe.Companion.getGmoRecipes(recipeManager);
        for (RecipeHolder<GmoRecipe> recipe : allGmoRecipes) {
            EntityType<?> entityType = recipe.value().getEntityType();
            ResourceKey<Gene> goodGene = recipe.value().getIdealGeneRk();

            ItemStack gmoCellStack = ModItems.INSTANCE.getGMO_CELL().toStack();
            GmoCell.Companion.setDetails(
                    gmoCellStack,
                    entityType,
                    ModGenes.INSTANCE.getHolderOrThrow(goodGene, registryAccess));

            recipes.add(new CellDupeRecipe(gmoCellStack));
        }

        return recipes;
    }
}
