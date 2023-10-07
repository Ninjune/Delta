package dev.ninjune.delta.features;

import dev.ninjune.delta.network.RecievePacketEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2APacketParticles;

import static dev.ninjune.delta.network.NetworkEventManager.pushEvent;

// goal is to highlight the beam from quazii as a square,
// perhaps find render library or figure it out with opengl

public class BeamHighlight extends RecievePacketEvent
{
    public BeamHighlight()
    {
        pushEvent(this);
    }

    @Override
    public void fire(Packet<?> packet)
    {
        if(!(packet instanceof S2APacketParticles)) return;
        // Implement here
    }
}