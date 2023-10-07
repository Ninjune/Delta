package dev.ninjune.delta.util;

import dev.ninjune.delta.util.CTLibs.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;


public class RenderUtil
{
    public static void drawEspBox (float x, float y, float z,
                     float red, float green, float blue, float alpha,
                     boolean phase
    )
    {
        GlStateManager.pushMatrix();
        GL11.glLineWidth(2.0F);
        GlStateManager.disableCull(); // disableCullFace
        GlStateManager.enableBlend(); // enableBlend
        GlStateManager.blendFunc(770, 771); // blendFunc
        GlStateManager.depthMask(false); // depthMask
        GlStateManager.disableTexture2D(); // disableTexture2D

        if(phase)
            GlStateManager.disableDepth(); // disableDepth

        final int[][][] locations = {
                //    x, y, z    x, y, z
                {
                        {0, 0, 0},
                        {1, 0, 0},
                },
                {
                        {0, 0, 0},
                        {0, 0, 1},
                },
                {
                        {1, 0, 1},
                        {1, 0, 0},
                },
                {
                        {1, 0, 1},
                        {0, 0, 1},
                },

                {
                        {0, 1, 0},
                        {1, 1, 0},
                },
                {
                        {0, 1, 0},
                        {0, 1, 1},
                },
                {
                        {1, 1, 1},
                        {1, 1, 0},
                },
                {
                        {1, 1, 1},
                        {0, 1, 1},
                },

                {
                        {0, 0, 0},
                        {0, 1, 0},
                },
                {
                        {1, 0, 0},
                        {1, 1, 0},
                },
                {
                        {0, 0, 1},
                        {0, 1, 1},
                },
                {
                        {1, 0, 1},
                        {1, 1, 1},
                },
        };

        for (int[][] loc : locations)
        {
            Tessellator.begin(3, true);
            Tessellator.colorize(red, green, blue, alpha);
            Tessellator.pos(x + loc[0][0] - 1f / 2, y + loc[0][1], z + loc[0][2] - 1f / 2);
            Tessellator.tex(0, 0);
            Tessellator.pos(x + loc[1][0] - 1f / 2, y + loc[1][1], z + loc[1][2] - 1f / 2);
            Tessellator.tex(0, 0);
            Tessellator.draw();
        }

        GlStateManager.enableCull(); // enableCull
        GlStateManager.disableBlend(); // disableBlend
        GlStateManager.depthMask(true); // depthMask
        GlStateManager.enableTexture2D(); // enableTexture2D

        if(phase)
            GlStateManager.enableDepth(); // enableDepth

        GlStateManager.popMatrix();
    }
}
