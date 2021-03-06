package mcjty.lib.gui;

import mcjty.lib.base.StyleConfig;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderHelper {
    public static float rot = 0.0f;

    public static void renderEntity(Entity entity, int xPos, int yPos) {
        GL11.glPushMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
        GL11.glEnable(2903 /* GL_COLOR_MATERIAL */);
        GL11.glPushMatrix();
        GL11.glTranslatef(xPos + 8, yPos + 16, 50F);
        float f1 = 10F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
//        entity.renderYawOffset = entity.rotationYaw = entity.prevRotationYaw = entity.prevRotationYawHead = entity.rotationYawHead = 0;//this.rotateTurret;
        entity.rotationPitch = 0.0F;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D,
                0.0F, 1.0F);
        GL11.glPopMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
        GL11.glTranslatef(0F, 0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
        int i1 = 240;
        int k1 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
                i1 / 1.0F, k1 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896 /* GL_LIGHTING */);
        GL11.glDisable(2929 /* GL_DEPTH_TEST */);
        GL11.glPopMatrix();
    }

    public static boolean renderObject(Minecraft mc, int x, int y, Object itm, boolean highlight) {
        if (itm instanceof Entity) {
            renderEntity((Entity) itm, x, y);
            return true;
        }
        RenderItem itemRender = new RenderItem();
        return renderObject(mc, itemRender, x, y, itm, highlight, 200);
    }

    public static boolean renderObject(Minecraft mc, RenderItem itemRender, int x, int y, Object itm, boolean highlight, float lvl) {
        itemRender.zLevel = lvl;

        if (itm==null) {
            return renderItemStack(mc, itemRender, null, x, y, "", highlight);
        }
        if (itm instanceof Item) {
            return renderItemStack(mc, itemRender, new ItemStack((Item) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof Block) {
            return renderItemStack(mc, itemRender, new ItemStack((Block) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof ItemStack) {
            return renderItemStackWithCount(mc, itemRender, (ItemStack) itm, x, y, highlight);
        }
        if (itm instanceof IIcon) {
            return renderIcon(mc, itemRender, (IIcon) itm, x, y, highlight);
        }
        return renderItemStack(mc, itemRender, null, x, y, "", highlight);
    }

    public static boolean renderIcon(Minecraft mc, RenderItem itemRender, IIcon itm, int xo, int yo, boolean highlight) {
        itemRender.renderIcon(xo, yo, itm, 16, 16);
        return true;
    }

    public static boolean renderItemStackWithCount(Minecraft mc, RenderItem itemRender, ItemStack itm, int xo, int yo, boolean highlight) {
        if (itm.stackSize==1 || itm.stackSize==0) {
            return renderItemStack(mc, itemRender, itm, xo, yo, "", highlight);
        } else {
            return renderItemStack(mc, itemRender, itm, xo, yo, "" + itm.stackSize, highlight);
        }
    }

    public static boolean renderItemStack(Minecraft mc, RenderItem itemRender, ItemStack itm, int x, int y, String txt, boolean highlight){
        GL11.glColor3f(1F, 1F, 1F);

        boolean isLightingEnabled = GL11.glIsEnabled(GL11.GL_LIGHTING);

        boolean rc = false;
        if (highlight){
            GL11.glDisable(GL11.GL_LIGHTING);
            drawVerticalGradientRect(x, y, x+16, y+16, 0x80ffffff, 0xffffffff);
        }
        if (itm != null && itm.getItem() != null) {
            rc = true;
            boolean isRescaleNormalEnabled = GL11.glIsEnabled(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            short short1 = 240;
            short short2 = 240;
            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
            itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), itm, x, y);
            itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), itm, x, y, txt);
            GL11.glPopMatrix();
            if (isRescaleNormalEnabled) {
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            } else {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            }
        }

        if (isLightingEnabled) {
            GL11.glEnable(GL11.GL_LIGHTING);
        } else {
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        return rc;
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
//        this.zLevel = 300.0F;
        float zLevel = 0.0f;

        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(x2, y1, zLevel);
        tessellator.addVertex(x1, y1, zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(x1, y2, zLevel);
        tessellator.addVertex(x2, y2, zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Draws a rectangle with a horizontal gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
//        this.zLevel = 300.0F;
        float zLevel = 0.0f;

        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(x1, y1, zLevel);
        tessellator.addVertex(x1, y2, zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(x2, y2, zLevel);
        tessellator.addVertex(x2, y1, zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1+1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1+1, y2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the left point
    public static void drawLeftTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x + 1, y - 1, y + 1, color);
        drawVerticalLine(x + 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the right point
    public static void drawRightTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x - 1, y - 1, y + 1, color);
        drawVerticalLine(x - 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the top point
    public static void drawUpTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x-1, y+1, x+1, color);
        drawHorizontalLine(x - 2, y + 2, x + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the bottom point
    public static void drawDownTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x-1, y-1, x+1, color);
        drawHorizontalLine(x-2, y-2, x+2, color);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawThickButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        Gui.drawRect(x1+2, y1+2, x2-2, y2-2, average);
        drawHorizontalLine(x1+1, y1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawHorizontalLine(x1+1, y2-1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x1, y1 + 1, y2 - 1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x2-1, y1+1, y2-1, StyleConfig.colorButtonExternalBorder);

        drawHorizontalLine(x1+1, y1+1, x2-1, bright);
        drawHorizontalLine(x1+2, y1+2, x2-2, bright);
        drawVerticalLine(x1+1, y1+2, y2-2, bright);
        drawVerticalLine(x1+2, y1+3, y2-3, bright);

        drawHorizontalLine(x1+3, y2-3, x2-2, dark);
        drawHorizontalLine(x1+2, y2-2, x2-1, dark);
        drawVerticalLine(x2 - 2, y1 + 2, y2 - 2, dark);
        drawVerticalLine(x2 - 3, y1 + 3, y2 - 3, dark);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawThinButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average);
        drawHorizontalLine(x1+1, y1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawHorizontalLine(x1+1, y2-1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x1, y1 + 1, y2 - 1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x2-1, y1+1, y2-1, StyleConfig.colorButtonExternalBorder);

        drawHorizontalLine(x1+1, y1+1, x2-2, bright);
        drawVerticalLine(x1 + 1, y1 + 2, y2 - 3, bright);

        drawHorizontalLine(x1 + 1, y2 - 2, x2 - 1, dark);
        drawVerticalLine(x2-2, y1+1, y2-2, dark);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawThinButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1, int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1+1, y1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawHorizontalLine(x1+1, y2-1, x2-1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x1, y1 + 1, y2 - 1, StyleConfig.colorButtonExternalBorder);
        drawVerticalLine(x2-1, y1+1, y2-1, StyleConfig.colorButtonExternalBorder);

        drawHorizontalLine(x1+1, y1+1, x2-2, bright);
        drawVerticalLine(x1 + 1, y1 + 2, y2 - 3, bright);

        drawHorizontalLine(x1 + 1, y2 - 2, x2 - 1, dark);
        drawVerticalLine(x2-2, y1+1, y2-2, dark);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        drawBeveledBox(x1, y1, x2, y2, bright, dark, average);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1, int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1, y1, x2-1, bright);
        drawVerticalLine(x1, y1, y2-1, bright);
        drawVerticalLine(x2-1, y1, y2-1, dark);
        drawHorizontalLine(x1, y2 - 1, x2, dark);
    }

    /**
     * Draw a beveled box. x2 and y2 are not included.
     */
    public static void drawBeveledBox(int x1, int y1, int x2, int y2, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1+1, y1+1, x2-1, y2-1, fillcolor);
        }
        drawHorizontalLine(x1, y1, x2-1, topleftcolor);
        drawVerticalLine(x1, y1, y2-1, topleftcolor);
        drawVerticalLine(x2-1, y1, y2-1, botrightcolor);
        drawHorizontalLine(x1, y2-1, x2, botrightcolor);
    }

    /**
     * Draw a thick beveled box. x2 and y2 are not included.
     */
    public static void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1+1, y1+1, x2-1, y2-1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2-1, y1+thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1+thickness, y2-1, topleftcolor);
        Gui.drawRect(x2-thickness, y1, x2, y2-1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    public static void renderBillboardQuad(double scale) {
        GL11.glPushMatrix();

        rotateToPlayer();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-scale, -scale, 0, 0, 0);
        tessellator.addVertexWithUV(-scale, +scale, 0, 0, 1);
        tessellator.addVertexWithUV(+scale, +scale, 0, 1, 1);
        tessellator.addVertexWithUV(+scale, -scale, 0, 1, 0);
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void renderBillboardQuadWithRotation(float rot, double scale) {
        GL11.glPushMatrix();

        rotateToPlayer();

//        GL11.glPushMatrix();

        GL11.glRotatef(rot, 0, 0, 1);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-scale, -scale, 0, 0, 0);
        tessellator.addVertexWithUV(-scale, +scale, 0, 0, 1);
        tessellator.addVertexWithUV(+scale, +scale, 0, 1, 1);
        tessellator.addVertexWithUV(+scale, -scale, 0, 1, 0);
        tessellator.draw();
//        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public static void rotateToPlayer() {
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
    }

    /**
     * Draw a beam with some thickness.
     * @param S
     * @param E
     * @param P
     * @param width
     */
    public static void drawBeam(Vector S, Vector E, Vector P, float width) {
        Vector PS = Sub(S, P);
        Vector SE = Sub(E, S);

        Vector normal = Cross(PS, SE);
        normal = normal.normalize();

        Vector half = Mul(normal, width);
        Vector p1 = Add(S, half);
        Vector p2 = Sub(S, half);
        Vector p3 = Add(E, half);
        Vector p4 = Sub(E, half);

        drawQuad(Tessellator.instance, p1, p3, p4, p2);
    }

    public static void drawQuad(Tessellator tessellator, Vector p1, Vector p2, Vector p3, Vector p4) {
        tessellator.addVertexWithUV(p1.getX(), p1.getY(), p1.getZ(), 0, 0);
        tessellator.addVertexWithUV(p2.getX(), p2.getY(), p2.getZ(), 1, 0);
        tessellator.addVertexWithUV(p3.getX(), p3.getY(), p3.getZ(), 1, 1);
        tessellator.addVertexWithUV(p4.getX(), p4.getY(), p4.getZ(), 0, 1);
    }

    public static class Vector {
        public final float x;
        public final float y;
        public final float z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public float norm() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }

        public Vector normalize() {
            float n = norm();
            return new Vector(x / n, y / n, z / n);
        }
    }

    private static Vector Cross(Vector a, Vector b) {
        float x = a.y*b.z - a.z*b.y;
        float y = a.z*b.x - a.x*b.z;
        float z = a.x*b.y - a.y*b.x;
        return new Vector(x, y, z);
    }

    private static Vector Sub(Vector a, Vector b) {
        return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
    }
    private static Vector Add(Vector a, Vector b) {
        return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
    }
    private static Vector Mul(Vector a, float f) {
        return new Vector(a.x * f, a.y * f, a.z * f);
    }


}
