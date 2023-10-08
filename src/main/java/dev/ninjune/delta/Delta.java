package dev.ninjune.delta;

import dev.ninjune.delta.config.DeltaConfig;
import dev.ninjune.delta.features.BeamHighlight;
import dev.ninjune.delta.features.GuardianCancel;
import dev.ninjune.delta.util.SBInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;

@Mod(modid = Delta.MODID, useMetadata=true)
public class Delta
{
    public static final String MODID = "delta";
    public static final String MODNAME = "Delta";
    public static final String PREFIX = "§7[§9Δ§7] §b";
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static ArrayList<Object> modules = new ArrayList<>();

    public static DeltaConfig config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, GuardianCancel::new);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        config = new DeltaConfig();
        loadModule(new BeamHighlight());
        loadModule(new SBInfo());
    }

    static void loadModule(Object obj)
    {
        modules.add(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
