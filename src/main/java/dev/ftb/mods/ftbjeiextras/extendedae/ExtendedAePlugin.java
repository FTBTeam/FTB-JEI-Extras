package dev.ftb.mods.ftbjeiextras.extendedae;

import appeng.recipes.AERecipeTypes;
import appeng.recipes.handlers.ChargerRecipe;
import appeng.recipes.handlers.InscriberRecipe;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.glodblock.github.extendedae.recipe.CrystalFixerRecipe;
import dev.ftb.mods.ftbjeiextras.loader.IConditionalModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;

public class ExtendedAePlugin implements IConditionalModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new CrystalFixerRecipeCategory(registration.getJeiHelpers()),
                new CircuitCutterRecipeCategory(registration.getJeiHelpers()),
                new AssemblerRecipeCategory(registration.getJeiHelpers())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;

        RecipeManager recipeManager = level.getRecipeManager();

        registration.addRecipes(CrystalFixerRecipeCategory.TYPE, recipeManager.getAllRecipesFor(CrystalFixerRecipe.TYPE));
        registration.addRecipes(CircuitCutterRecipeCategory.TYPE, recipeManager.getAllRecipesFor(CircuitCutterRecipe.TYPE));
        registration.addRecipes(AssemblerRecipeCategory.TYPE, recipeManager.getAllRecipesFor(CrystalAssemblerRecipe.TYPE));

        registration.addItemStackInfo(new ItemStack(EAESingletons.ENTRO_CRYSTAL), Component.translatable("emi.extendedae.desc.entro_crystal"));
        registration.addItemStackInfo(new ItemStack(EAESingletons.ENTRO_SEED), Component.translatable("emi.extendedae.desc.entro_seed"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        RecipeType<RecipeHolder<InscriberRecipe>> inscriber = RecipeType.createFromVanilla(AERecipeTypes.INSCRIBER);
        RecipeType<RecipeHolder<ChargerRecipe>> charger = RecipeType.createFromVanilla(AERecipeTypes.CHARGER);

        registration.addRecipeCatalyst(new ItemStack(EAESingletons.EX_INSCRIBER), inscriber);
        registration.addRecipeCatalyst(new ItemStack(EAESingletons.EX_CHARGER), charger);
        registration.addRecipeCatalyst(new ItemStack(EAESingletons.CRYSTAL_FIXER), CrystalFixerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(EAESingletons.CIRCUIT_CUTTER), CircuitCutterRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(EAESingletons.CRYSTAL_ASSEMBLER), AssemblerRecipeCategory.TYPE);
    }


    @Override
    public boolean shouldLoad() {
        return ModList.get().isLoaded("extendedae");
    }
}

