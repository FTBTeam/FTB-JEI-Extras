package dev.ftb.mods.ftbjeiextras.advancedae;

import dev.ftb.mods.ftbjeiextras.loader.IConditionalModPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.fml.ModList;
import net.pedroksl.advanced_ae.common.definitions.AAEBlocks;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public class AdvancedAePlugin implements IConditionalModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ReactionChamberRecipeCategory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AAEBlocks.REACTION_CHAMBER), ReactionChamberRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;

        var recipeManager = level.getRecipeManager();

        registration.addRecipes(ReactionChamberRecipeCategory.TYPE, recipeManager.getAllRecipesFor(ReactionChamberRecipe.TYPE).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public boolean shouldLoad() {
        return ModList.get().isLoaded("advanced_ae");
    }
}
