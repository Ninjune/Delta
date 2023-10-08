package dev.ninjune.delta.features;

import dev.ninjune.delta.config.DeltaConfig;
import dev.ninjune.delta.util.SBInfo;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGuardian;

public class GuardianCancel extends RenderGuardian
{
    public GuardianCancel(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(!(SBInfo.getLocation() != "Crimson Isle" && SBInfo.getLocation() != "The End") ||
                !(DeltaConfig.beamHighlight && DeltaConfig.hideOriginalBeam)
        )
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}