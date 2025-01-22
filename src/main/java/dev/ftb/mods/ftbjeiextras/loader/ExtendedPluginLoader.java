package dev.ftb.mods.ftbjeiextras.loader;

import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import dev.ftb.mods.ftbjeiextras.extendedae.ExtendedAePlugin;
import dev.ftb.mods.ftbjeiextras.geneticsresequenced.GeneticsResequencedPlugin;
import dev.ftb.mods.ftbjeiextras.oritech.OritechPlugin;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.config.IJeiConfigManager;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;

/**
 * Essentially a delegator for the plugins that are conditionally loaded to allow for safe access when some mods
 * are present and some mods are missing
 */
@SuppressWarnings("unused")
@JeiPlugin
public class ExtendedPluginLoader implements IModPlugin {
    final List<IConditionalModPlugin> containedPlugins = List.of(
            new ExtendedAePlugin(),
            new OritechPlugin(),
            new GeneticsResequencedPlugin()
    );

    @Override
    public ResourceLocation getPluginUid() {
        return FTBJeiExtras.id("extended_plugins_loader");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        runForEach(plugin -> plugin.registerItemSubtypes(registration));
    }

    @Override
    public <T> void registerFluidSubtypes(ISubtypeRegistration registration, IPlatformFluidHelper<T> platformFluidHelper) {
        runForEach(plugin -> plugin.registerFluidSubtypes(registration, platformFluidHelper));
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        runForEach(plugin -> plugin.registerIngredients(registration));
    }

    @Override
    public void registerIngredientAliases(IIngredientAliasRegistration registration) {
        runForEach(plugin -> plugin.registerIngredientAliases(registration));
    }

    @Override
    public void registerModInfo(IModInfoRegistration modAliasRegistration) {
        runForEach(plugin -> plugin.registerModInfo(modAliasRegistration));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        runForEach(plugin -> plugin.registerCategories(registration));
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        runForEach(plugin -> plugin.registerVanillaCategoryExtensions(registration));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        runForEach(plugin -> plugin.registerRecipes(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        runForEach(plugin -> plugin.registerRecipeTransferHandlers(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        runForEach(plugin -> plugin.registerRecipeCatalysts(registration));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        runForEach(plugin -> plugin.registerGuiHandlers(registration));
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        runForEach(plugin -> plugin.registerAdvanced(registration));
    }

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        runForEach(plugin -> plugin.registerRuntime(registration));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runForEach(plugin -> plugin.onRuntimeAvailable(jeiRuntime));
    }

    @Override
    public void onRuntimeUnavailable() {
        runForEach(IConditionalModPlugin::onRuntimeUnavailable);
    }

    @Override
    public void onConfigManagerAvailable(IJeiConfigManager configManager) {
        runForEach(plugin -> plugin.onConfigManagerAvailable(configManager));
    }

    private void runForEach(Consumer<IConditionalModPlugin> runnable) {
        for (IConditionalModPlugin plugin : containedPlugins) {
            if (!plugin.shouldLoad()) {
                continue;
            }

            runnable.accept(plugin);
        }
    }
}
