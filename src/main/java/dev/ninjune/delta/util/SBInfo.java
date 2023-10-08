package dev.ninjune.delta.util;

import dev.ninjune.delta.util.ctwrappers.TabList;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SBInfo
{
    private static String location = "";
    public static boolean inSkyblock = false;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event)
    {
        new Thread(() -> {
            try
            {
                Thread.sleep(1000, 0); // arbitrary number
                location = intGetLocation();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    public static String getLocation()
    {
        return location;
    }

    private static String intGetLocation()
    {
        List<String> tab = TabList.getNames();
        final Pattern pattern = Pattern.compile("/Area|Dungeons: ([\\w ]+)/");

        for(String name : tab)
        {
            Matcher matcher = pattern.matcher(name);
            if(matcher.find())
                return matcher.group();
        }

        return "";
    }
}
