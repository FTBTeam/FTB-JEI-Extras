package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

public class OrganicMatterRecipe implements ConversionRecipe {
    private final EntityType<?> entityType;
    private final Ingredient matter;
    private final ItemStack cell;

    public OrganicMatterRecipe(EntityType<?> entityType) {
        this.entityType = entityType;

        ItemStack matter = ModItems.INSTANCE.getORGANIC_MATTER().toStack();
        EntityDnaItem.Companion.setEntityType(matter, entityType);

        ItemStack cell = ModItems.INSTANCE.getCELL().toStack();
        EntityDnaItem.Companion.setEntityType(cell, entityType);

        this.matter = DataComponentIngredient.of(false, matter);
        this.cell = cell;

    }

    @Override
    public Ingredient getInput() {
        return matter;
    }

    @Override
    public ItemStack getOutput() {
        return cell;
    }
}
