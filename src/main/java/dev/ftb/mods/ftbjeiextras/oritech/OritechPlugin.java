package dev.ftb.mods.ftbjeiextras.oritech;

import dev.ftb.mods.ftbjeiextras.loader.IConditionalModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;
import rearth.oritech.block.entity.generators.BioGeneratorEntity;
import rearth.oritech.block.entity.generators.FuelGeneratorEntity;
import rearth.oritech.block.entity.generators.LavaGeneratorEntity;
import rearth.oritech.block.entity.generators.SteamEngineEntity;
import rearth.oritech.block.entity.processing.AssemblerBlockEntity;
import rearth.oritech.block.entity.processing.AtomicForgeBlockEntity;
import rearth.oritech.block.entity.processing.CentrifugeBlockEntity;
import rearth.oritech.block.entity.processing.FoundryBlockEntity;
import rearth.oritech.block.entity.processing.FragmentForgeBlockEntity;
import rearth.oritech.block.entity.processing.PulverizerBlockEntity;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.recipes.OritechRecipe;
import rearth.oritech.init.recipes.RecipeContent;

import java.util.function.Supplier;

public class OritechPlugin implements IConditionalModPlugin {

    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> PULVERIZER = RecipeType.createFromDeferredVanilla(() -> RecipeContent.PULVERIZER);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> GRINDER = RecipeType.createFromDeferredVanilla(() -> RecipeContent.GRINDER);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> ASSEMBLER = RecipeType.createFromDeferredVanilla(() -> RecipeContent.ASSEMBLER);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> FOUNDRY = RecipeType.createFromDeferredVanilla(() -> RecipeContent.FOUNDRY);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> CENTRIFUGE = RecipeType.createFromDeferredVanilla(() -> RecipeContent.CENTRIFUGE);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> CENTRIFUGE_FLUID = RecipeType.createFromDeferredVanilla(() -> RecipeContent.CENTRIFUGE_FLUID);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> ATOMIC_FORGE = RecipeType.createFromDeferredVanilla(() -> RecipeContent.ATOMIC_FORGE);

    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> BIO_GENERATOR = RecipeType.createFromDeferredVanilla(() -> RecipeContent.BIO_GENERATOR);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> FUEL_GENERATOR = RecipeType.createFromDeferredVanilla(() -> RecipeContent.FUEL_GENERATOR);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> LAVA_GENERATOR = RecipeType.createFromDeferredVanilla(() -> RecipeContent.LAVA_GENERATOR);
    private static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> STEAM_ENGINE = RecipeType.createFromDeferredVanilla(() -> RecipeContent.STEAM_ENGINE);

    public static final Supplier<RecipeType<RecipeHolder<OritechRecipe>>> PARTICLE_COLLISION = RecipeType.createFromDeferredVanilla(() -> RecipeContent.PARTICLE_COLLISION);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
               new OritechRecipeCategory(registration.getJeiHelpers(), PULVERIZER.get(), PulverizerBlockEntity.class, BlockContent.PULVERIZER_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), GRINDER.get(), FragmentForgeBlockEntity.class, BlockContent.FRAGMENT_FORGE_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), ASSEMBLER.get(), AssemblerBlockEntity.class, BlockContent.ASSEMBLER_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), FOUNDRY.get(), FoundryBlockEntity.class, BlockContent.FOUNDRY_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), CENTRIFUGE.get(), CentrifugeBlockEntity.class, BlockContent.CENTRIFUGE_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), CENTRIFUGE_FLUID.get(), CentrifugeBlockEntity.class, BlockContent.CENTRIFUGE_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), ATOMIC_FORGE.get(), AtomicForgeBlockEntity.class, BlockContent.ATOMIC_FORGE_BLOCK),

                new OritechRecipeCategory(registration.getJeiHelpers(), BIO_GENERATOR.get(), BioGeneratorEntity.class, BlockContent.BIO_GENERATOR_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), FUEL_GENERATOR.get(), FuelGeneratorEntity.class, BlockContent.FUEL_GENERATOR_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), LAVA_GENERATOR.get(), LavaGeneratorEntity.class, BlockContent.LAVA_GENERATOR_BLOCK),
                new OritechRecipeCategory(registration.getJeiHelpers(), STEAM_ENGINE.get(), SteamEngineEntity.class, BlockContent.STEAM_ENGINE_BLOCK),

                new OritechParticleCollisionRecipe(registration.getJeiHelpers(), PARTICLE_COLLISION.get())
        );
    }

    @Override
    public boolean shouldLoad() {
        return ModList.get().isLoaded("oritech");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;

        RecipeManager recipeManager = level.getRecipeManager();

        registration.addRecipes(PULVERIZER.get(), recipeManager.getAllRecipesFor(RecipeContent.PULVERIZER));
        registration.addRecipes(GRINDER.get(), recipeManager.getAllRecipesFor(RecipeContent.GRINDER));
        registration.addRecipes(ASSEMBLER.get(), recipeManager.getAllRecipesFor(RecipeContent.ASSEMBLER));
        registration.addRecipes(FOUNDRY.get(), recipeManager.getAllRecipesFor(RecipeContent.FOUNDRY));
        registration.addRecipes(CENTRIFUGE.get(), recipeManager.getAllRecipesFor(RecipeContent.CENTRIFUGE));
        registration.addRecipes(CENTRIFUGE_FLUID.get(), recipeManager.getAllRecipesFor(RecipeContent.CENTRIFUGE_FLUID));
        registration.addRecipes(ATOMIC_FORGE.get(), recipeManager.getAllRecipesFor(RecipeContent.ATOMIC_FORGE));

        registration.addRecipes(BIO_GENERATOR.get(), recipeManager.getAllRecipesFor(RecipeContent.BIO_GENERATOR));
        registration.addRecipes(FUEL_GENERATOR.get(), recipeManager.getAllRecipesFor(RecipeContent.FUEL_GENERATOR));
        registration.addRecipes(LAVA_GENERATOR.get(), recipeManager.getAllRecipesFor(RecipeContent.LAVA_GENERATOR));
        registration.addRecipes(STEAM_ENGINE.get(), recipeManager.getAllRecipesFor(RecipeContent.STEAM_ENGINE));

        registration.addRecipes(PARTICLE_COLLISION.get(), recipeManager.getAllRecipesFor(RecipeContent.PARTICLE_COLLISION));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockContent.PULVERIZER_BLOCK), PULVERIZER.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.FRAGMENT_FORGE_BLOCK), GRINDER.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.ASSEMBLER_BLOCK), ASSEMBLER.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.FOUNDRY_BLOCK), FOUNDRY.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.CENTRIFUGE_BLOCK), CENTRIFUGE.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.CENTRIFUGE_BLOCK), CENTRIFUGE_FLUID.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.ATOMIC_FORGE_BLOCK), ATOMIC_FORGE.get());

        registration.addRecipeCatalyst(new ItemStack(BlockContent.BIO_GENERATOR_BLOCK), BIO_GENERATOR.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.FUEL_GENERATOR_BLOCK), FUEL_GENERATOR.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.LAVA_GENERATOR_BLOCK), LAVA_GENERATOR.get());
        registration.addRecipeCatalyst(new ItemStack(BlockContent.STEAM_ENGINE_BLOCK), STEAM_ENGINE.get());

        registration.addRecipeCatalyst(new ItemStack(BlockContent.ACCELERATOR_CONTROLLER), PARTICLE_COLLISION.get());
    }
}
