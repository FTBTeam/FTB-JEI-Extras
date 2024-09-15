package dev.ftb.mods.ftbjeiextras;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(FTBJeiExtras.MODID)
public class FTBJeiExtras {
    public static final String MODID = "ftbjeiextras";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FTBJeiExtras(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
