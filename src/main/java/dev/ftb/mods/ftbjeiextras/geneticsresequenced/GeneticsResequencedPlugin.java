package dev.ftb.mods.ftbjeiextras.geneticsresequenced;

import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.BloodPurifierRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.CellAnalyzerRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.DNADecryptRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.DNAExtractorRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.PlasmidInfuserRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.PlasmidInjectorRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.CellToHelixRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.DecryptHelixRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.OrganicMatterRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInfuserRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInjectorRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PurifySyringeRecipe;
import dev.ftb.mods.ftbjeiextras.loader.IConditionalModPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;

import java.util.List;

public class GeneticsResequencedPlugin implements IConditionalModPlugin {

    @Override
    public boolean shouldLoad() {
        return ModList.get().isLoaded("geneticsresequenced");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new BloodPurifierRecipeCategory(registration.getJeiHelpers()),
                new CellAnalyzerRecipeCategory(registration.getJeiHelpers()),
                new DNAExtractorRecipeCategory(registration.getJeiHelpers()),
                new DNADecryptRecipeCategory(registration.getJeiHelpers()),
                new PlasmidInfuserRecipeCategory(registration.getJeiHelpers()),
                new PlasmidInjectorRecipeCategory(registration.getJeiHelpers(), ModBlocks.INSTANCE.getPLASMID_INJECTOR().toStack())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        RegistryAccess registries = Minecraft.getInstance().level.registryAccess();
        registration.addRecipes(BloodPurifierRecipeCategory.TYPE, List.of(new PurifySyringeRecipe(true), new PurifySyringeRecipe(false)));
        registration.addRecipes(CellAnalyzerRecipeCategory.TYPE, EntityDnaItem.Companion.getValidEntityTypes().stream().map(OrganicMatterRecipe::new).toList());
        registration.addRecipes(DNAExtractorRecipeCategory.TYPE, CellToHelixRecipe.collectAllRecipes(recipeManager, registries));
        registration.addRecipes(DNADecryptRecipeCategory.TYPE, DecryptHelixRecipe.collectAllRecipes(registries));
        registration.addRecipes(PlasmidInfuserRecipeCategory.TYPE, PlasmidInfuserRecipe.collectAllRecipes(registries));
        registration.addRecipes(PlasmidInjectorRecipeCategory.TYPE, PlasmidInjectorRecipe.collectAllRecipes(registries));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getBLOOD_PURIFIER(), BloodPurifierRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getCELL_ANALYZER(), CellAnalyzerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getDNA_EXTRACTOR(), DNAExtractorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getDNA_DECRYPTOR(), DNADecryptRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getPLASMID_INFUSER(), PlasmidInfuserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getPLASMID_INJECTOR(), PlasmidInjectorRecipeCategory.TYPE);
    }
}
