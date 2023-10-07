package dev.ninjune.delta.util.CTLibs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static java.lang.Math.sqrt;

public class Tessellator {
    private static final Tessellator instance = new Tessellator();
    private static final net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
    private static final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    private static final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

    private static boolean firstVertex = true;
    private static boolean began = false;


    /**
     * Disables alpha in the GLStateManager.
     * @return itself
     */
    public static void disableAlpha() {
        GlStateManager.disableAlpha();
    }


    /**
     * Enables alpha in the GLStateManager.
     * @return itself
     */
    public static void enableAlpha() {
        GlStateManager.enableAlpha();
    }


    public static void alphaTessellatorc(int Tessellatorc , float ref) {
        GlStateManager.alphaFunc(Tessellatorc, ref);
    }


    public static void enableLighting() {
        GlStateManager.enableLighting();
    }


    public static void disableLighting() {
        GlStateManager.disableLighting();
    }


    public static void disableDepth() {
        GlStateManager.disableDepth();
    }


    public static void enableDepth() {
        GlStateManager.enableDepth();
    }


    public static void depthTessellatorc(int depthTessellatorc) {
        GlStateManager.clearDepth(depthTessellatorc);
    }


    public static void depthMask(Boolean flagIn) {
        GlStateManager.depthMask(flagIn);
    }


    public static void disableBlend() {
        GlStateManager.disableBlend();
    }


    public static void enableBlend() {
        GlStateManager.enableBlend();
    }


    public static void blendTessellatorc(int sourceFactor, int destFactor) {
        GlStateManager.blendFunc(sourceFactor, destFactor);
    }


    public static void tryBlendTessellatorcSeparate(int sourceFactor, int destFactor, int sourceFactorAlpha, int destFactorAlpha) {
        GlStateManager.tryBlendFuncSeparate(sourceFactor, destFactor, sourceFactorAlpha, destFactorAlpha);
        
    }


    public static void enableTexture2D() {
        GlStateManager.enableTexture2D();
    }


    public static void disableTexture2D() {
        GlStateManager.disableTexture2D();
    }

    /*
     * Binds a texture to the client for the Tessellator to use.
     *
     * @param texture the texture to bind
     * 

    Tessellator bindTexture(texture: Image) {
        GlStateManager.bindTexture(texture.getTexture().glTextureId)
    }*/

    /*
    Tessellator deleteTexture(texture: Image) {
        GlStateManager.deleteTexture(texture.getTexture().glTextureId)
    }*/


    public static void pushMatrix() {
        GlStateManager.pushMatrix();
    }


    public static void popMatrix() {
        GlStateManager.popMatrix();
    }

    /**
     * Begin drawing with the Tessellator
     * with default draw mode of quads and textured
     *
     * @param drawMode the GL draw mode default 7
     * @param textured if the Tessellator is textured default true
     */
    public static void begin(int drawMode, boolean textured) {
        pushMatrix();
        enableBlend();
        tryBlendTessellatorcSeparate(770, 771, 1, 0);

        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);

        worldRenderer.begin(
            drawMode,
            textured ? DefaultVertexFormats.POSITION_TEX : DefaultVertexFormats.POSITION
        );
        firstVertex = true;
        began = true;
        
    }

    /**
     * Colorize the Tessellator.
     *
     * @param red the red value between 0 and 1
     * @param green the green value between 0 and 1
     * @param blue the blue value between 0 and 1
     * @param alpha the alpha value between 0 and 1 default 1f
     */
    public static void colorize(float red, float green, float blue, float alpha) {
        GlStateManager.color(red, green, blue, alpha);
        
    }

    /**
     * Rotates the Tessellator in 3d space.
     * Similar to [com.chattriggers.ctjs.minecraft.libs.renderer.Renderer.rotate]
     *
     * @param angle the angle to rotate
     * @param x if the rotation is around the x axis
     * @param y if the rotation is around the y axis
     * @param z if the rotation is around the z axis
     */
    public static void rotate(float angle, float x, float y, float z) {
        GlStateManager.rotate(angle, x, y, z);
    }

    /**
     * Translates the Tessellator in 3d space.
     * Similar to [com.chattriggers.ctjs.minecraft.libs.renderer.Renderer.translate]
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position
     */
    public static void translate(float x, float y, float z) {
        GlStateManager.translate(x, y, z);
    }

    /**
     * Scales the Tessellator in 3d space.
     * Similar to [com.chattriggers.ctjs.minecraft.libs.renderer.Renderer.scale]
     *
     * @param x scale in the x direction
     * @param y scale in the y direction
     * @param z scale in the z direction
     */
    public static void scale(float x, float y, float z) {
        GlStateManager.scale(x, y, z);
    }

    /**
     * Sets a new vertex in the Tessellator.
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position
     * 
     */
    public static void pos(double x, double y, double z) {
        if (!began)
            begin(7, true);
        if (!firstVertex)
            worldRenderer.endVertex();
        worldRenderer.pos(x, y, z);
        firstVertex = false;
    }

    /**
     * Sets the texture location on the last defined vertex.
     * Use directly after using [Tessellator.pos]
     *
     * @param u the u position in the texture
     * @param v the v position in the texture
     * 
     */
    public static void tex(float u, float v) {
        worldRenderer.tex(u, v);
    }

    /**
     * Finalizes and draws the Tessellator.
     */
    public static void draw() {
        if (!began) return;

        worldRenderer.endVertex();

        tessellator.draw();

        colorize(1f, 1f, 1f, 1f);

        began = false;

        disableBlend();
        popMatrix();
    }

    /*
     * Gets a fixed render position from x, y, and z inputs adjusted with partial ticks
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return the Vector3f position to render at

    Vector3f getRenderPos(float x, float y, float z) {
        return Vector3f(
            x - Player.getRenderX(),
            y - Player.getRenderY(),
            z - Player.getRenderZ()
        );
    }*/

    /*
     * Renders floating lines of text in the 3D world at a specific position.
     *
     * @param text The string array of text to render
     * @param x X coordinate in the game world
     * @param y Y coordinate in the game world
     * @param z Z coordinate in the game world
     * @param color the color of the text default 0xffffffff.toInt()
     * @param renderBlackBox render a pretty black border behind the text default true
     * @param scale the scale of the text default 1f
     * @param increase whether to scale the text up as the player moves away default true

    void drawString
    (
        String text,
        float x,
        float y,
        float z,
        int color,
        boolean renderBlackBox,
        float scale,
        boolean increase
    ) {
        float lScale = scale;

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        Vector3f renderPos = getRenderPos(x, y, z);

        if (increase) {
            float distance = (float) sqrt(renderPos.x * renderPos.x + renderPos.y * renderPos.y + renderPos.z * renderPos.z);
            float multiplier = (distance / 120f); //mobs only render ~120 blocks away
            lScale *= 0.45f * multiplier;
        }

        int xMultiplier = Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1;

        GlStateManager.color(1f, 1f, 1f, 0.5f);
        pushMatrix();
        translate(renderPos.x, renderPos.y, renderPos.z);
        rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        rotate(renderManager.playerViewX * xMultiplier, 1.0f, 0.0f, 0.0f);
        scale(-lScale, -lScale, lScale);
        disableLighting();
        depthMask(false);
        disableDepth();
        enableBlend();
        blendTessellatorc(770, 771);

        int textWidth = fontRenderer.getStringWidth(text);

        if (renderBlackBox) {
            int j = textWidth / 2;
            disableTexture2D();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos((-j - 1), (-1), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos((-j - 1), 8, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos((j + 1), 8, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos((j + 1), (-1), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            enableTexture2D();
        }

        fontRenderer.drawString(text, -textWidth / 2, 0, color);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        depthMask(true);
        enableDepth();
        popMatrix();
    }*/
}