package dev.ninjune.delta;

import dev.ninjune.delta.config.DeltaConfig;
import dev.ninjune.delta.features.*;
import dev.ninjune.delta.util.SBInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Delta.MODID, useMetadata=true, version = Delta.VERSION, clientSideOnly = true)
public class Delta
{
    public static final String MODID = "delta";
    public static final String MODNAME = "Delta";
    public static final String PREFIX = "§7[§9Δ§7] §b";
    public static final String VERSION = "1.1.1";
    public static final Minecraft mc = Minecraft.getMinecraft();

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
        loadModule(new NoRender());
        loadModule(new UpdateMessage());
        loadModule(new SBInfo());
    }

    private static void loadModule(Object obj)
    {
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
