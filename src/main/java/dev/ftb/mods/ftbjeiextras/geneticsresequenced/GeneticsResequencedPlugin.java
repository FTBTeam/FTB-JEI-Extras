package dev.ftb.mods.ftbjeiextras.geneticsresequenced;

import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem;
import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.BloodPurifierRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.CellAnalyzerRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.DNADecryptRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.DNAExtractorRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.BasicIncubatorRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.BlackDeathRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.CellDupeRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.GMORecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.SetPotionEntityRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.Incubator.ViruisRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.PlasmidInfuserRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.category.PlasmidInjectorRecipeCategory;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.CellToHelixRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.DecryptHelixRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.OrganicMatterRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInfuserRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PlasmidInjectorRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.PurifySyringeRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.BasicIncubatorRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.BlackDeathRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.CellDupeRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.GMORecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.SetPotionEntityRecipe;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.recipes.incubator.VirusRecipe;
import dev.ftb.mods.ftbjeiextras.loader.IConditionalModPlugin;
import mezz.jei.api.constants.RecipeTypes;
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
                new PlasmidInjectorRecipeCategory(registration.getJeiHelpers()),
                new BasicIncubatorRecipeCategory(registration.getJeiHelpers()),
                new CellDupeRecipeCategory(registration.getJeiHelpers()),
                new SetPotionEntityRecipeCategory(registration.getJeiHelpers()),
                new ViruisRecipeCategory(registration.getJeiHelpers()),
                new BlackDeathRecipeCategory(registration.getJeiHelpers()),
                new GMORecipeCategory(registration.getJeiHelpers())
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
        registration.addRecipes(BasicIncubatorRecipeCategory.TYPE, BasicIncubatorRecipe.collectAllRecipes(recipeManager));
        registration.addRecipes(CellDupeRecipeCategory.TYPE, CellDupeRecipe.collectAllRecipes(recipeManager, registries));
        registration.addRecipes(SetPotionEntityRecipeCategory.TYPE, SetPotionEntityRecipe.collectAllRecipes(recipeManager));
        registration.addRecipes(ViruisRecipeCategory.TYPE, VirusRecipe.collectAllRecipes(recipeManager, registries));
        registration.addRecipes(BlackDeathRecipeCategory.TYPE, BlackDeathRecipe.collectAllRecipes(registries));
        registration.addRecipes(GMORecipeCategory.TYPE, GMORecipe.collectAllRecipes(recipeManager, registries));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getBLOOD_PURIFIER(), BloodPurifierRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getCELL_ANALYZER(), CellAnalyzerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getDNA_EXTRACTOR(), DNAExtractorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getDNA_DECRYPTOR(), DNADecryptRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getPLASMID_INFUSER(), PlasmidInfuserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getPLASMID_INJECTOR(), PlasmidInjectorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), RecipeTypes.BREWING);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), RecipeTypes.BREWING);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), BasicIncubatorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), CellDupeRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), SetPotionEntityRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), ViruisRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getINCUBATOR(), BlackDeathRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), GMORecipeCategory.TYPE);


        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), BasicIncubatorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), CellDupeRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), SetPotionEntityRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), ViruisRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), BlackDeathRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.INSTANCE.getADVANCED_INCUBATOR(), GMORecipeCategory.TYPE);
    }
}
