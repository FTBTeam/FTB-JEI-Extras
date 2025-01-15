package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.gene.Gene;
import dev.aaronhowser.mods.geneticsresequenced.item.PlasmidItem;
import dev.aaronhowser.mods.geneticsresequenced.item.SyringeItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import dev.aaronhowser.mods.geneticsresequenced.util.ClientUtil;
import dev.ftb.mods.ftbjeiextras.modspecific.GeneticsHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PlasmidInjectorRecipe {

    private final Ingredient plasmid;
    private final Ingredient syringeBefore;
    private final ItemStack syringeAfter;

    public PlasmidInjectorRecipe(Holder<Gene> geneHolder, boolean isMetal, boolean isAntiPlasmid) {
        ItemStack plasmidStack = isAntiPlasmid ? ModItems.INSTANCE.getANTI_PLASMID().toStack() : ModItems.INSTANCE.getPLASMID().toStack();
        PlasmidItem.Companion.setGene(plasmidStack, geneHolder, geneHolder.value().getDnaPointsRequired());
        this.plasmid = Ingredient.of(plasmidStack);

        ItemStack syringeStack = isMetal ? ModItems.INSTANCE.getMETAL_SYRINGE().toStack() : ModItems.INSTANCE.getSYRINGE().toStack();

        LivingEntity entity = GeneticsHelpers.createLivingEntity(isMetal);

        SyringeItem.Companion.setEntity(syringeStack, entity, false);
        this.syringeBefore = Ingredient.of(syringeStack);

        if (isAntiPlasmid) {
            SyringeItem.Companion.addAntigene(syringeStack, geneHolder);
        } else {
            SyringeItem.Companion.addGene(syringeStack, geneHolder);
        }

        this.syringeAfter = syringeStack;
    }

    public Ingredient getPlasmid() {
        return plasmid;
    }

    public Ingredient getSyringeBefore() {
        return syringeBefore;
    }

    public ItemStack getSyringeAfter() {
        return syringeAfter;
    }

    public static List<PlasmidInjectorRecipe> collectAllRecipes(RegistryAccess registryAccess) {
        List<PlasmidInjectorRecipe> recipes = new ArrayList<>();
        for (Holder<Gene> geneHolder : ModGenes.INSTANCE.getRegistrySorted(registryAccess, true, false)) {
            recipes.add(new PlasmidInjectorRecipe(geneHolder, false, false));
            recipes.add(new PlasmidInjectorRecipe(geneHolder, true, false));
            recipes.add(new PlasmidInjectorRecipe(geneHolder, false, true));
            recipes.add(new PlasmidInjectorRecipe(geneHolder, true, true));
        }
        return recipes;
    }
}
