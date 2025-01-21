package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.DnaHelixItem;
import dev.aaronhowser.mods.geneticsresequenced.item.SyringeItem;
import dev.aaronhowser.mods.geneticsresequenced.recipe.BrewingRecipes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class BlackDeathRecipe implements IncubatorRecipe{

    private final Ingredient input;
    private final ItemStack output;
    private final Ingredient ingredient;

    public BlackDeathRecipe(boolean isMetal, RegistryAccess registryAccess) {
        ItemStack syringe = isMetal ? ModItems.INSTANCE.getMETAL_SYRINGE().toStack() : ModItems.INSTANCE.getSYRINGE().toStack();
        LivingEntity entity = GeneticsHelpers.createLivingEntity(isMetal);
        SyringeItem.Companion.setEntity(syringe, entity, false);

        List<Holder<Gene>> requiredGenes = dev.aaronhowser.mods.geneticsresequenced.recipe.incubator.BlackDeathRecipe.Companion.getRequiredGenes(registryAccess);
        for (Holder<Gene> requiredGene : requiredGenes) {
            SyringeItem.Companion.addGene(syringe, requiredGene);
        }

        this.ingredient = Ingredient.of(syringe);
        this.input = Ingredient.of(BrewingRecipes.INSTANCE.getViralAgentsPotionStack());
        this.output = DnaHelixItem.Companion.getHelixStack(ModGenes.INSTANCE.getBASIC(), registryAccess);
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

    public static List<BlackDeathRecipe> collectAllRecipes(RegistryAccess registryAccess) {
        return List.of(new BlackDeathRecipe(true, registryAccess), new BlackDeathRecipe(false, registryAccess));
    }
}
