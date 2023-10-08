package dev.ninjune.delta.features;

import dev.ninjune.delta.config.DeltaConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import dev.ninjune.delta.util.RenderUtil;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static dev.ninjune.delta.Delta.mc;

// goal is to highlight the beam from quazii as a rectangle,
// perhaps find render library or figure it out with opengl

public class BeamHighlight
{
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        if(!DeltaConfig.beamHighlight) return;
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

            BlockPos gPos = guardian.getPosition();
            Vector3f gPosVec = new Vector3f(gPos.getX(), gPos.getY(), gPos.getZ());

            BlockPos tPos = target.getPosition();
            Vector3f tPosVec = new Vector3f(tPos.getX(), tPos.getY(), tPos.getZ());

            RenderUtil.drawRectangle(gPosVec, tPosVec, 0f, 0.7f,0.7f, 0.5f, false);
            RenderUtil.drawRectangleOutline(gPosVec, tPosVec, 0f, 0.7f,0.7f, 1f, false);
        }
    }
}