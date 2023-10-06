package dev.ninjune.delta;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;

@Mod(modid = Delta.MODID, useMetadata=true)
public class Delta
{
    public static final String MODID = "delta";
    public static final String PREFIX = "§7[§9Δ§7] §b";
    private static ArrayList modules = new ArrayList<Object>();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("Dirt: " + Blocks.dirt.getUnlocalizedName());
    }

    static void loadModule(Object obj)
    {
        modules.add(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
