package dev.ninjune.delta.features;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import dev.ninjune.delta.network.RecievePacketEvent;
import dev.ninjune.delta.util.RenderUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static dev.ninjune.delta.Delta.mc;
import static dev.ninjune.delta.network.NetworkEventManager.pushEvent;

// goal is to highlight the beam from quazii as a square,
// perhaps find render library or figure it out with opengl

public class BeamHighlight extends RecievePacketEvent
{
    public BeamHighlight()
    {
        pushEvent(this);
    }
    private ArrayList<S2APacketParticles> particles = new ArrayList<>();

    @Override
    public void fire(Packet<?> packet)
    {
        if(!(packet instanceof S2APacketParticles)) return;
        //RenderUtil.drawEspBox(packet);
        S2APacketParticles particle = ((S2APacketParticles) packet);

        if(particle.getParticleType() == EnumParticleTypes.SPELL_MOB)
            particles.add(particle);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        /*System.out.println(particles.size());
        for(int i = 0; i < particles.size(); i++)
        {
            S2APacketParticles particle = particles.get(i);
            RenderUtil.drawEspBox((float) particle.getXCoordinate(),
                    (float) particle.getYCoordinate(),
                    (float) particle.getZCoordinate(),
                    1, 1, 0f, 0.7f, 0.7f, 1f, true
            );
        }*/
        List<Entity> entities = mc.theWorld.loadedEntityList;
        ArrayList<EntityGuardian> guardians = new ArrayList<>();

        for(Entity entity : entities)
            if(entity instanceof EntityGuardian)
                guardians.add((EntityGuardian) entity);

        for (EntityGuardian guardian : guardians)
        {
            if(!guardian.hasTargetedEntity()) continue;

            EntityLivingBase target = guardian.getTargetedEntity();
            if(target == null)
                continue;

            for (Entity entity2 : entities)
            {
                if(entity2 instanceof EntityGuardian &&
                        ((EntityGuardian) entity2).hasTargetedEntity() &&
                        Math.sqrt(entity2.getDistanceSq(guardian.getPosition())) < 5 // arbitrary number
                )
                {
                    if(entity2 == null || ((EntityGuardian) entity2).getTargetedEntity() == null)
                        continue;
                    if(entity2.posY > guardian.posY)
                        guardian = (EntityGuardian) entity2;
                    if(((EntityGuardian) entity2).getTargetedEntity().posY < target.posY)
                        target = ((EntityGuardian) entity2).getTargetedEntity();
                }
            } // basically find the minimum and maximum target and guardian.

            float gX = (float) guardian.posX, gY = (float) guardian.posY, gZ = (float) guardian.posZ;
            float tX = (float) target.posX, tY = (float) target.posY, tZ = (float) target.posZ;
            RenderUtil.drawRectangularPrism(gX, gY, gZ, tX, tY, tZ,
                    0f, 0.7f,0.7f, 1f, true
            );
        }
    }
}