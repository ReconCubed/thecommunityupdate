package me.reconcubed.communityupdate.tileentity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.reconcubed.communityupdate.block.OneWayGlassBlock;
import me.reconcubed.communityupdate.init.ModBlocks;
import me.reconcubed.communityupdate.tileentity.OneWayGlassTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import org.lwjgl.opengl.GL11;

public class OneWayGlassRenderer extends TileEntityRenderer<OneWayGlassTileEntity> {

    @Override
    public void render(OneWayGlassTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x, y, z);
            GlStateManager.translated(0.5, 0.5,0.5);

            BlockState state = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
            if (state.getBlock() != ModBlocks.ONE_WAY_GLASS.get()) return;
            GlStateManager.rotatef(state.get(OneWayGlassBlock.FACING).getHorizontalIndex() * -90F + 180F, 0, 1, 0);

            GlStateManager.translated(-0.5, -0.5, -0.5);
        }
        GlStateManager.popMatrix();
    }

    private static void drawCuboid(double x, double y, double z, double width, double height, double depth)
    {
        x /= 16;
        y /= 16;
        z /= 16;
        width /= 16;
        height /= 16;
        depth /= 16;
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.normal3f(0.0F, 1.0F, 0.0F);
        drawQuad(x + (1 - width), y, z, x + width + (1 - width), y + height, z, Direction.NORTH);
        drawQuad(x + 1, y, z, x + 1, y + height, z + depth, Direction.EAST);
        drawQuad(x + width + 1 - (width + width), y, z + depth, x + width + 1 - (width + width), y + height, z, Direction.WEST);
        drawQuad(x + (1 - width), y, z + depth, x + width + (1 - width), y, z, Direction.DOWN);
        drawQuad(x + (1 - width), y + height, z, x + width + (1 - width), y, z + depth, Direction.UP);
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
    }

    private static void drawQuad(double xFrom, double yFrom, double zFrom, double xTo, double yTo, double zTo, Direction facing)
    {
        double textureWidth = Math.abs(xTo - xFrom);
        double textureHeight = Math.abs(yTo - yFrom);
        double textureDepth = Math.abs(zTo - zFrom);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        switch(facing.getAxis())
        {
            case X:
                buffer.pos(xFrom, yFrom, zFrom).tex(1 - xFrom + textureDepth, 1 - yFrom + textureHeight).endVertex();
                buffer.pos(xFrom, yTo, zFrom).tex(1 - xFrom + textureDepth, 1 - yFrom).endVertex();
                buffer.pos(xTo, yTo, zTo).tex(1 - xFrom, 1 - yFrom).endVertex();
                buffer.pos(xTo, yFrom, zTo).tex(1 - xFrom, 1 - yFrom + textureHeight).endVertex();
                break;
            case Y:
                buffer.pos(xFrom, yFrom, zFrom).tex(1 - xFrom + textureWidth, 1 - yFrom + textureDepth).endVertex();
                buffer.pos(xFrom, yFrom, zTo).tex(1 - xFrom + textureWidth, 1 - yFrom).endVertex();
                buffer.pos(xTo, yFrom, zTo).tex(1 - xFrom, 1 - yFrom).endVertex();
                buffer.pos(xTo, yFrom, zFrom).tex(1 - xFrom, 1 - yFrom + textureDepth).endVertex();
                break;
            case Z:
                buffer.pos(xFrom, yFrom, zFrom).tex(1 - xFrom + textureWidth, 1 - yFrom + textureHeight).endVertex();
                buffer.pos(xFrom, yTo, zFrom).tex(1 - xFrom + textureWidth, 1 - yFrom).endVertex();
                buffer.pos(xTo, yTo, zTo).tex(1 - xFrom, 1 - yFrom).endVertex();
                buffer.pos(xTo, yFrom, zTo).tex(1 - xFrom, 1 - yFrom + textureHeight).endVertex();
                break;
        }
        tessellator.draw();
    }


}
