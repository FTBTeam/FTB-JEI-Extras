package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.recipe.BrewingRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class VirusRecipe implements IncubatorRecipe {

    private final Ingredient ingredient;
    private final Ingredient input;
    private final ItemStack output;

    public VirusRecipe(ResourceKey<Gene> input, ResourceKey<Gene> output, RegistryAccess registryAccess) {
        this.ingredient = Ingredient.of(DnaHelixItem.Companion.getHelixStack(input, registryAccess));
        this.input = Ingredient.of(BrewingRecipes.INSTANCE.getViralAgentsPotionStack());
        this.output = DnaHelixItem.Companion.getHelixStack(output, registryAccess);
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

    public static List<VirusRecipe> collectAllRecipes(RecipeManager recipeManager, RegistryAccess registryAccess) {
        List<VirusRecipe> recipes = new ArrayList<>();
        for (RecipeHolder<dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.VirusRecipe> virusRecipe : dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.VirusRecipe.Companion.getVirusRecipes(recipeManager)) {
            recipes.add(new VirusRecipe(virusRecipe.value().getInputDnaGene(), virusRecipe.value().getOutputGene(), registryAccess));
        }
        return recipes;
    }
}
