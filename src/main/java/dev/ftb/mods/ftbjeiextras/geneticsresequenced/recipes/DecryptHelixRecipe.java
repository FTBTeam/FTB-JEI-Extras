package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.data.EntityGenes;
import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecryptHelixRecipe implements ConversionRecipe {

    private final Ingredient input;
    private final ItemStack output;
    private final float chance;

    public DecryptHelixRecipe(EntityType<?> entityType, Holder<Gene> gene, float chance) {
        ItemStack stack = ModItems.INSTANCE.getDNA_HELIX().toStack();
        EntityDnaItem.Companion.setEntityType(stack, entityType);
        input = DataComponentIngredient.of(true, stack);

        ItemStack helixStack = DnaHelixItem.Companion.getHelixStack(gene);
        this.output = helixStack;
        this.chance = chance;
    }

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public float getChance() {
        return chance;
    }

    public static List<DecryptHelixRecipe> collectAllRecipes(RegistryAccess registries) {
        List<DecryptHelixRecipe> recipes = new ArrayList<>();

        Map<EntityType<?>, Map<Holder<Gene>, Integer>> entityGeneHolderMap = EntityGenes.Companion.getEntityGeneHolderMap(registries);
        entityGeneHolderMap.forEach((entityType, geneHolderMap) -> {
            int weight = geneHolderMap.values().stream().mapToInt(Integer::intValue).sum();

            geneHolderMap.forEach((gene, geneWeight) -> {
                float chance = (float) geneWeight / weight;
                recipes.add(new DecryptHelixRecipe(entityType, gene, chance));
            });
        });

        return recipes;
    }


}
