package dev.ninjune.delta.features;

import cc.polyfrost.oneconfig.events.event.WorldLoadEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.BufferedReader;
import java.io.IOException;

import static dev.ninjune.delta.util.ApiUtils.request;

public class UpdateMessage
{
    private static boolean sentUpdateMessage = false;
    private static boolean shouldSendMessage = false;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event)
    {
        if(!shouldSendMessage || sentUpdateMessage) return;


    }


    /*@SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        try
        {
            BufferedReader req = request("https://raw.githubusercontent.com/Ninjune/Delta/master/gradle.properties");
            req.readLine();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }*/
}
