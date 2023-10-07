package dev.ninjune.delta;

import dev.ninjune.delta.config.DeltaConfig;
import dev.ninjune.delta.features.BeamHighlight;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;

@Mod(modid = Delta.MODID, useMetadata=true)
public class Delta
{
    public static final String MODID = "delta";
    public static final String MODNAME = "Delta";
    public static final String PREFIX = "§7[§9Δ§7] §b";
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static ArrayList modules = new ArrayList<Object>();

    public static DeltaConfig config;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        config = new DeltaConfig();
        loadModule(new BeamHighlight());
    }

    static void loadModule(Object obj)
    {
        System.out.println("HI FROM LOADMODULE");
        modules.add(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
