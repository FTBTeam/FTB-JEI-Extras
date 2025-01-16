package dev.ftb.mods.ftbjeiextras.modspecific;

import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class GeneticsHelpers {

    public static final String MODID = "geneticsresequenced";

    public static LivingEntity createLivingEntity(boolean isMetal) {
        LocalPlayer clientPlayer = MinecraftHelpers.getClientPlayer();
        return isMetal ? EntityType.COW.create(clientPlayer.level()) : clientPlayer;
    }

    public static Component categoryName(String key) {
        return Component.translatable("emi.category." + MODID + "." + key);
    }

    public static <T> RecipeType<T> createRecipeType(String key, Class<T> recipeClass) {
        return RecipeType.create(FTBJeiExtras.MODID, "gr_" + key, recipeClass);
    }

    public static ResourceLocation rl(String key) {
        return ResourceLocation.fromNamespaceAndPath(MODID, key);
    }
}
