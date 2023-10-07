package dev.ninjune.delta.network;

import net.minecraft.network.Packet;

public interface INetworkEvent
{
    void fire(Packet<?> packet);
}
