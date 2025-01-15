package dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes;

import dev.aaronhowser.mods.geneticsresequenced.item.SyringeItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

public class PurifySyringeRecipe implements ConversionRecipe {

    private final boolean isMetal;
    private final Ingredient contaminatedSyringe;
    private final ItemStack decontaminatedSyringe;

    public PurifySyringeRecipe(boolean isMetal) {
        this.isMetal = isMetal;
        ItemStack stack = isMetal ? ModItems.INSTANCE.getMETAL_SYRINGE().toStack() : ModItems.INSTANCE.getSYRINGE().toStack();
        Player player = Minecraft.getInstance().player;
        LivingEntity entity = isMetal ? EntityType.COW.create(player.level()) : player;
        SyringeItem.Companion.setEntity(stack, entity, true);
        this.contaminatedSyringe = DataComponentIngredient.of(true, stack);
        this.decontaminatedSyringe = stack;
    }

    public boolean isMetal() {
        return isMetal;
    }
    @Override
    public Ingredient getInput() {
        return contaminatedSyringe;
    }

    @Override
    public ItemStack getOutput() {
        return decontaminatedSyringe;
    }
}
