package dev.ninjune.delta.network;

import java.util.ArrayList;

public class NetworkEventManager
{
    private static ArrayList<INetworkEvent> events = new ArrayList<INetworkEvent>();

    public static void pushEvent(INetworkEvent event)
    {
        events.add(event);
    }

    public static ArrayList<INetworkEvent> getEvents()
    {
        return events;
    }
}
