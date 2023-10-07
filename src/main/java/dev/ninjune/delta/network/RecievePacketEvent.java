package dev.ninjune.delta.network;

import net.minecraft.network.Packet;


abstract public class RecievePacketEvent implements INetworkEvent
{
    abstract public void fire(Packet<?> packet);
}
