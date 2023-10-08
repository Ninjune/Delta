package dev.ninjune.delta.util.ctwrappers;

import net.minecraft.client.gui.GuiPlayerTabOverlay;

import static dev.ninjune.delta.Delta.mc;

public class Client
{
    public static GuiPlayerTabOverlay getTabGui()
    {
        try
        {
            return mc.ingameGUI.getTabList();
        } catch (NullPointerException npe)
        {
            return null;
        }
    }
}
