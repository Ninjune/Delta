package dev.ninjune.delta.util;

import dev.ninjune.delta.util.ctlibs.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;


public class RenderUtil
{
    public static void drawEspBox (float x, float y, float z,
                     float wx, float h, float wz, float red, float green, float blue, float alpha,
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

        final float[][][] locations = {
                //    x, y, z    x, y, z
                {
                        {0, 0, 0},
                        {wx, 0, 0},
                },
                {
                        {0, 0, 0},
                        {0, 0, wz},
                },
                {
                        {wx, 0, wz},
                        {wx, 0, 0},
                },
                {
                        {wx, 0, wz},
                        {0, 0, wz},
                },

                {
                        {0, h, 0},
                        {wx, h, 0},
                },
                {
                        {0, h, 0},
                        {0, h, wz},
                },
                {
                        {wx, h, wz},
                        {wx, h, 0},
                },
                {
                        {wx, h, wz},
                        {0, h, wz},
                },

                {
                        {0, 0, 0},
                        {0, h, 0},
                },
                {
                        {wx, 0, 0},
                        {wx, h, 0},
                },
                {
                        {0, 0, wz},
                        {0, h, wz},
                },
                {
                        {wx, 0, wz},
                        {wx, h, wz},
                },
        };

        for (float[][] loc : locations)
        {
            Tessellator.begin(3, true);
            Tessellator.colorize(red, green, blue, alpha);
            Tessellator.pos(x + loc[0][0] - wx / 2f, y + loc[0][1], z + loc[0][2] - wz / 2f);
            Tessellator.tex(0, 0);
            Tessellator.pos(x + loc[1][0] - wx / 2f, y + loc[1][1], z + loc[1][2] - wz / 2f);
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

    public static void drawLine(float x1, float y1, float z1,
                               float x2, float y2, float z2, float red, float green, float blue, float alpha,
                               boolean phase)
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


        Tessellator.begin(GL11.GL_LINES, true);
        Tessellator.colorize(red, green, blue, alpha);

        // Calculate the direction vector between two points
        double dirX = x2 - x1;
        double dirY = y2 - y1;
        double dirZ = z2 - z1;

        // Normalize the direction vector
        double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX /= length;
        dirY /= length;
        dirZ /= length;

        // Calculate the rotation angles
        double angleX = Math.atan2(dirY, dirZ);
        double angleY = Math.atan2(dirZ, dirX) + 3.1415/2;

        // Calculate vertices for the line segment
        double startX = x1 - Math.sin(angleY);
        double startY = y1 - Math.sin(angleX);
        double startZ = z1 + Math.cos(angleY);
        double endX = x2 + Math.sin(angleY);
        double endY = y2 + Math.sin(angleX);
        double endZ = z2 - Math.cos(angleY);

        addLine(startX, startY, startZ, endX, endY, endZ);

        Tessellator.draw();

        GlStateManager.enableCull(); // enableCull
        GlStateManager.disableBlend(); // disableBlend
        GlStateManager.depthMask(true); // depthMask
        GlStateManager.enableTexture2D(); // enableTexture2D

        if(phase)
            GlStateManager.enableDepth(); // enableDepth

        GlStateManager.popMatrix();
    }


    public static void drawRectangularPrism(Vector3f start, Vector3f end,
                                            float depth, float red, float green, float blue, float alpha, boolean phase, int glMode) {
        // Calculate the direction vector from start to end points
        Vector3f direction = (Vector3f) new Vector3f(end.x - start.x, end.y - start.y, end.z - start.z).normalise();

        // Calculate perpendicular vectors
        Vector3f right = (Vector3f) new Vector3f(direction.z, 0, -direction.x).normalise().scale(depth / 2);
        Vector3f up = (Vector3f) Vector3f.cross(direction, right, null).normalise().scale(depth / 2);

        // Calculate vertices for the prism
        Vector3f frontTopLeft = new Vector3f(start.x - right.x + up.x, start.y, start.z - right.z + up.z);
        Vector3f frontBottomLeft = new Vector3f(start.x - right.x - up.x, end.y, start.z - right.z - up.z);
        Vector3f frontBottomRight = new Vector3f(start.x + right.x - up.x, end.y + right.y - up.y, start.z + right.z - up.z);
        Vector3f frontTopRight = new Vector3f(start.x + right.x + up.x, start.y, start.z + right.z + up.z);

        Vector3f backTopLeft = new Vector3f(end.x - right.x + up.x, start.y, end.z - right.z + up.z);
        Vector3f backBottomLeft = new Vector3f(end.x - right.x - up.x, end.y, end.z - right.z - up.z);
        Vector3f backBottomRight = new Vector3f(end.x + right.x - up.x, end.y, end.z + right.z - up.z);
        Vector3f backTopRight = new Vector3f(end.x + right.x + up.x, start.y, end.z + right.z + up.z);

        // Draw front face
        drawRectangle4(frontTopLeft, frontBottomLeft, frontBottomRight, frontTopRight, red, green, blue, alpha, phase, glMode);

        // Draw back face
        drawRectangle4(backTopLeft, backBottomLeft, backBottomRight, backTopRight, red, green, blue, alpha, phase, glMode);

        // Draw top face
        drawRectangle4(frontTopLeft, frontTopRight, backTopRight, backTopLeft, red, green, blue, alpha, phase, glMode);

        // Draw bottom face
        drawRectangle4(frontBottomLeft, frontBottomRight, backBottomRight, backBottomLeft, red, green, blue, alpha, phase, glMode);

        // Draw left face
        drawRectangle4(frontTopLeft, frontBottomLeft, backBottomLeft, backTopLeft, red, green, blue, alpha, phase, glMode);

        // Draw right face
        drawRectangle4(frontTopRight, frontBottomRight, backBottomRight, backTopRight, red, green, blue, alpha, phase, glMode);
    }


    public static void drawRectangle2(Vector3f topLeft, Vector3f bottomRight,
                                            float red, float green, float blue, float alpha, boolean phase
    )
    {
        GlStateManager.pushMatrix();
        GL11.glLineWidth(2.0f);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();

        if (phase)
            GlStateManager.disableDepth();

        double x1 = topLeft.x, y1 = topLeft.y, z1 = topLeft.z;
        double x2 = bottomRight.x, y2 = bottomRight.y, z2 = bottomRight.z;

        Tessellator.begin(GL11.GL_QUADS, true);
        Tessellator.colorize(red, green, blue, alpha);

        addLine(x1, y1, z1, x2, y1, z2);

        pushPos(x2,y2,z2);
        pushPos(x1, y2, z1);

        pushPos(x1, y1, z1);

        Tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();

        if (phase)
            GlStateManager.enableDepth();

        GlStateManager.popMatrix();
    }


    private static void drawRectangle4(Vector3f topLeft, Vector3f bottomLeft, Vector3f bottomRight, Vector3f topRight,
                                       float red, float green, float blue, float alpha, boolean phase, int glMode)
    {
        GlStateManager.pushMatrix();
        GL11.glLineWidth(2.0f);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();

        if (phase)
            GlStateManager.disableDepth();

        Tessellator.begin(glMode, true);
        Tessellator.colorize(red, green, blue, alpha);

        pushPos(topLeft);
        pushPos(bottomLeft);
        pushPos(bottomRight);
        pushPos(topRight);
        pushPos(topLeft);

        Tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();

        if (phase)
            GlStateManager.enableDepth();

        GlStateManager.popMatrix();
    }


    public static void drawRectangleOutline(Vector3f topLeft, Vector3f bottomRight,
                                            float red, float green, float blue, float alpha, boolean phase)
    {
        GlStateManager.pushMatrix();
        GL11.glLineWidth(2.0f);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();

        if (phase)
            GlStateManager.disableDepth();

        double x1 = topLeft.x, y1 = topLeft.y, z1 = topLeft.z;
        double x2 = bottomRight.x, y2 = bottomRight.y, z2 = bottomRight.z;

        Tessellator.begin(GL11.GL_LINE_STRIP, true);
        Tessellator.colorize(red, green, blue, alpha);

        addLine(x1, y1, z1, x2, y1, z2);

        pushPos(x2,y2,z2);
        pushPos(x1, y2, z1);

        pushPos(x1, y1, z1);

        Tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();

        if (phase)
            GlStateManager.enableDepth();

        GlStateManager.popMatrix();
    }

    public static Vector3f interpolatePosition(Vector3f startPos, Vector3f endPos, float partialTicks)
    {
        float interpX = startPos.x + (endPos.x - startPos.x) * partialTicks;
        float interpY = startPos.y + (endPos.y - startPos.y) * partialTicks;
        float interpZ = startPos.z + (endPos.z - startPos.z) * partialTicks;
        return new Vector3f(interpX, interpY, interpZ);
    }

    private static void addLine(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        pushPos(x1, y1, z1);
        pushPos(x2, y2, z2);
    }


    private static void pushPos(double x, double y, double z)
    {
        Tessellator.pos(x,y,z);
        Tessellator.tex(0, 0);
    }


    private static void pushPos(Vector3f pos)
    {
        pushPos(pos.x, pos.y, pos.z);
    }
}
