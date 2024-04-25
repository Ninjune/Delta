package dev.ninjune.delta.features;

import dev.ninjune.delta.config.DeltaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender
{
    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<?> event)
    {
        if(!DeltaConfig.slimeNoRender)
            return;
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.setRenderShadow(true);

        if (event.entity instanceof EntitySlime)
        {
            renderManager.setRenderShadow(false);
            event.setCanceled(true);
        }
        else if(event.entity instanceof EntityArmorStand && event.entity.getName().contains("Sludge"))
            event.setCanceled(true);
    }
}