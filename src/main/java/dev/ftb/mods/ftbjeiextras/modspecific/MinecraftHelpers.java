package dev.ftb.mods.ftbjeiextras.modspecific;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class MinecraftHelpers {

    public static LocalPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
