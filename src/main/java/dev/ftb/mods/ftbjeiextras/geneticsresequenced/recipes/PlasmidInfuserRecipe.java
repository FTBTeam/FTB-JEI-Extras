package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.item.PlasmidItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PlasmidInfuserRecipe implements ConversionRecipe {

    private final Ingredient input;
    private final ItemStack output;

    public PlasmidInfuserRecipe(Holder<Gene> geneHolder, boolean basic) {
        ItemStack stack =  DnaHelixItem.Companion.getHelixStack(basic ? ModGenes.INSTANCE.getHolderOrThrow(ModGenes.INSTANCE.getBASIC(), Minecraft.getInstance().player.registryAccess()) : geneHolder);

        this.input = Ingredient.of(stack);

        ItemStack plasmid = ModItems.INSTANCE.getPLASMID().toStack();;
        PlasmidItem.Companion.setGene(plasmid, geneHolder, geneHolder.value().getDnaPointsRequired());
        this.output = plasmid;
    }

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }


    public static List<PlasmidInfuserRecipe> collectAllRecipes(RegistryAccess registries) {
        List<PlasmidInfuserRecipe> recipes = new ArrayList<>();

        ModGenes.INSTANCE.getRegistrySorted(registries, false, false).forEach((geneHolder) -> {
            recipes.add(new PlasmidInfuserRecipe(geneHolder, true));
            recipes.add(new PlasmidInfuserRecipe(geneHolder, false));
        });

        return recipes;
    }
}
