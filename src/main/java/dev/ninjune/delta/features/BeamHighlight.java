package dev.ninjune.delta.features;

import dev.ninjune.delta.config.DeltaConfig;
import dev.ninjune.delta.util.SBInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import dev.ninjune.delta.util.RenderUtil;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dev.ninjune.delta.Delta.mc;
import static dev.ninjune.delta.util.RenderUtil.interpolatePosition;

// goal is to highlight the beam from quazii as a rectangle,
// perhaps find render library or figure it out with opengl

public class BeamHighlight
{
    private final HashMap<Entity, Vector3f> prevPositions = new HashMap<>();

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        if(!DeltaConfig.beamHighlight)
            return;
        //System.out.println(SBInfo.getLocation());
        List<Entity> entities = mc.theWorld.loadedEntityList;
        ArrayList<EntityGuardian> guardians = new ArrayList<>();

        for(Entity entity : entities)
            if(entity instanceof EntityGuardian)
                guardians.add((EntityGuardian) entity);

        ArrayList<EntityGuardian> maxGuardians = new ArrayList<>();
        ArrayList<EntityLivingBase> minTargets = new ArrayList<>(); // should be a one to one correlation of Guardians to Targets

        for (EntityGuardian guardian : guardians)
        {
            if(!guardian.hasTargetedEntity()) continue;

            EntityLivingBase target = guardian.getTargetedEntity();
            if(target == null)
                continue;
            boolean maxInRange = false;
            boolean minInRange = false;

            for(int i = 0; i < maxGuardians.size(); i++)
            {
                if(Math.sqrt(guardian.getDistanceSq(maxGuardians.get(i).getPosition())) < 5) // arbitrary number
                {
                    if(guardian.posY > maxGuardians.get(i).posY)
                        maxGuardians.set(i, guardian);
                    maxInRange = true;
                }
                if(Math.sqrt(target.getDistanceSq(minTargets.get(i).getPosition())) < 5)
                {
                    if(target.posY < minTargets.get(i).posY)
                        minTargets.set(i, target);
                    minInRange = true;
                }
            }

            if(!maxInRange || !minInRange)
            {
                maxGuardians.add(guardian);
                minTargets.add(target);
            }
        }

        for(int i = 0; i < maxGuardians.size(); i++)
        {
            EntityGuardian guardian = maxGuardians.get(i);
            EntityLivingBase target = minTargets.get(i);

            // Get or create previous positions
            Vector3f prevGuardianPos = prevPositions.computeIfAbsent(guardian, k -> new Vector3f((float) guardian.posX, (float) guardian.posY, (float) guardian.posZ));
            Vector3f prevTargetPos = prevPositions.computeIfAbsent(target, k -> new Vector3f((float) target.posX, (float) target.posY, (float) target.posZ));

            // Interpolate positions
            Vector3f interpGuardianPos = interpolatePosition(prevGuardianPos, new Vector3f((float) guardian.posX, (float) guardian.posY, (float) guardian.posZ), event.partialTicks);
            Vector3f interpTargetPos = interpolatePosition(prevTargetPos, new Vector3f((float) target.posX, (float) target.posY, (float) target.posZ), event.partialTicks);

            if(DeltaConfig.beam2d)
            {
                RenderUtil.drawRectangle2(interpGuardianPos, interpTargetPos,  0f, 0.7f,0.7f, 0.15f, false);
                RenderUtil.drawRectangleOutline(interpGuardianPos, interpTargetPos,  0f, 0.7f,0.7f, 1f, false);
            }
            else
            {
                RenderUtil.drawRectangularPrism(interpGuardianPos, interpTargetPos, 0.5f, 0f, 0.7f,0.7f, 0.15f, false, GL11.GL_QUADS);
                RenderUtil.drawRectangularPrism(interpGuardianPos, interpTargetPos, 0.5f, 0f, 0.7f,0.7f, 1f, false, GL11.GL_LINE_STRIP);
            }

            prevPositions.put(guardian, interpGuardianPos);
            prevPositions.put(target, interpTargetPos);
        }
    }
}