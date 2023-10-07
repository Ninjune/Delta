package dev.ninjune.delta.network;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2APacketParticles;

import java.util.ArrayList;

abstract public class RecievePacketEvent implements INetworkEvent
{
    abstract public void fire(Packet<?> packet);
}
