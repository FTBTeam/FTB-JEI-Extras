package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.GmoRecipe;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModPotions;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class GMORecipe
{
    private final Ingredient ingredient;
    private final Ingredient input;
    private final ItemStack goodOutput;
    private final ItemStack badOutput;
    private final float chance;

    public GMORecipe(EntityType<?> entityType, Ingredient ingredient, ResourceKey<Gene> gene, float chance, boolean mutation, RegistryAccess registryAccess) {
        DeferredHolder<Potion, Potion> requiredPotion = mutation ? ModPotions.INSTANCE.getMUTATION() :  ModPotions.INSTANCE.getCELL_GROWTH();
        ItemStack potionStack = PotionContents.createItemStack(Items.POTION, requiredPotion);

        EntityDnaItem.Companion.setEntityType(potionStack, entityType);
        input = Ingredient.of(potionStack);

        ItemStack gmoStack = ModItems.INSTANCE.getGMO_CELL().toStack();
        EntityDnaItem.Companion.setEntityType(gmoStack, entityType);
        DnaHelixItem.Companion.setGeneHolder(gmoStack, ModGenes.INSTANCE.getHolderOrThrow(gene, registryAccess));
        this.goodOutput = gmoStack;

        ItemStack failCell = ModItems.INSTANCE.getGMO_CELL().toStack();
        EntityDnaItem.Companion.setEntityType(failCell, entityType);
        DnaHelixItem.Companion.setGeneHolder(failCell, ModGenes.INSTANCE.getHolderOrThrow(ModGenes.INSTANCE.getBASIC(), registryAccess));
        badOutput = failCell;
        this.ingredient = ingredient;
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getBadOutput() {
        return badOutput;
    }

    public ItemStack getGoodOutput() {
        return goodOutput;
    }

    public static List<GMORecipe> collectAllRecipes(RecipeManager recipeManager, RegistryAccess registryAccess) {
        List<GMORecipe> recipes = new ArrayList<>();
        for (RecipeHolder<GmoRecipe> gmoRecipe : GmoRecipe.Companion.getGmoRecipes(recipeManager)) {
            GmoRecipe value = gmoRecipe.value();
            recipes.add(new GMORecipe(value.getEntityType(), value.getTopIngredient(), value.getIdealGeneRk(), value.getGeneChance(), value.getNeedsMutationPotion(), registryAccess));
        }
        return recipes;
    }
}
