package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.item.GmoCell;
import dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.GmoRecipe;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import software.bernie.geckolib.util.ClientUtil;

import java.util.ArrayList;
import java.util.List;

public class CellToHelixRecipe implements ConversionRecipe{

    private final ItemStack input;
    private final ItemStack output;

    public CellToHelixRecipe(ItemStack cell, ItemStack helix) {
        this.input = cell;
        this.output = helix;
    }

    @Override
    public Ingredient input() {
        return Ingredient.of(input);
    }

    @Override
    public ItemStack output() {
        return output;
    }

    public static List<CellToHelixRecipe> collectAllRecipes(RecipeManager recipeManager, RegistryAccess registries) {
        List<CellToHelixRecipe> recipes = new ArrayList<>();

        for (EntityType<?> validEntityType : EntityDnaItem.Companion.getValidEntityTypes()) {
            ItemStack cell = ModItems.INSTANCE.getCELL().toStack();
            EntityDnaItem.Companion.setEntityType(cell, validEntityType);

            ItemStack helix = ModItems.INSTANCE.getDNA_HELIX().toStack();
            EntityDnaItem.Companion.setEntityType(helix, validEntityType);

            recipes.add(new CellToHelixRecipe(cell, helix));
        }

        for (RecipeHolder<GmoRecipe> gmoRecipe : GmoRecipe.Companion.getGmoRecipes(recipeManager)) {
            EntityType<?> entityType = gmoRecipe.value().getEntityType();
            ResourceKey<Gene> idealGeneRk = gmoRecipe.value().getIdealGeneRk();

            ItemStack geneStack = ModItems.INSTANCE.getGMO_CELL().toStack();
            GmoCell.Companion.setDetails(geneStack, entityType, ModGenes.INSTANCE.getHolderOrThrow(idealGeneRk, registries));

            ItemStack helixStack = DnaHelixItem.Companion.getHelixStack(idealGeneRk, registries);
            recipes.add(new CellToHelixRecipe(geneStack, helixStack));

            boolean doesExist = recipes.stream()
                    .anyMatch(cellToHelixRecipe -> {
                        Holder<Gene> geneHolder = DnaHelixItem.Companion.getGeneHolder(cellToHelixRecipe.output);
                        return EntityDnaItem.Companion.getEntityType(cellToHelixRecipe.input) == entityType && geneHolder != null && geneHolder.is(ModGenes.INSTANCE.getBASIC());
                    });

            if (doesExist) {
                continue;
            }

            ItemStack badStack = ModItems.INSTANCE.getGMO_CELL().toStack();
            GmoCell.Companion.setDetails(badStack, entityType, ModGenes.INSTANCE.getHolderOrThrow(ModGenes.INSTANCE.getBASIC(), registries));

            ItemStack outoutBadStack = DnaHelixItem.Companion.getHelixStack(ModGenes.INSTANCE.getBASIC(), registries);
            recipes.add(new CellToHelixRecipe(badStack, outoutBadStack));
        }

        return recipes;
    }
}
