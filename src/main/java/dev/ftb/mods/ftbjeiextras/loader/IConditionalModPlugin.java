package dev.ftb.mods.ftbjeiextras.loader;

import dev.ftb.mods.ftbjeiextras.FTBJeiExtras;
import mezz.jei.api.IModPlugin;
import net.minecraft.resources.ResourceLocation;

public interface IConditionalModPlugin extends IModPlugin {
    boolean shouldLoad();

    @Override
    default ResourceLocation getPluginUid() {
        return FTBJeiExtras.id("this_is_never_used");
    }
}
