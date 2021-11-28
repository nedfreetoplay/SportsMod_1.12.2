package net.sports.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.sports.client.client_Sports;

public class RenderBlock implements ISimpleBlockRenderingHandler
{
  public static Tessellator tessellator;
  
  public boolean shouldRender3DInInventory() {
    return true;
  }
  
  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
    tessellator = Tessellator.instance;
    
    tessellator.startDrawingQuads();
    tessellator.setNormal(0.0F, -1.0F, 0.0F);
    
    double k = -0.5D, j = k, i = j;
    IIcon icon = block.getIcon(0, 0);
    double u0 = icon.getMinU();
    double u1 = icon.getMaxU();
    double v0 = icon.getMinV();
    double v1 = icon.getMaxV();
    
    tessellator.addVertexWithUV(i, j, k, u1, v1);
    tessellator.addVertexWithUV(i, j + 1.0D, k, u1, v0);
    tessellator.addVertexWithUV(i, j + 1.0D, k + 1.0D, u0, v0);
    tessellator.addVertexWithUV(i, j, k + 1.0D, u0, v1);
    
    tessellator.addVertexWithUV(i + 1.0D, j, k, u1, v1);
    tessellator.addVertexWithUV(i + 1.0D, j + 1.0D, k, u1, v0);
    tessellator.addVertexWithUV(i + 1.0D, j + 1.0D, k + 1.0D, u0, v0);
    tessellator.addVertexWithUV(i + 1.0D, j, k + 1.0D, u0, v1);
    
    tessellator.addVertexWithUV(i + 1.0D, j, k, u1, v1);
    tessellator.addVertexWithUV(i + 1.0D, j + 1.0D, k, u1, v0);
    tessellator.addVertexWithUV(i, j + 1.0D, k, u0, v0);
    tessellator.addVertexWithUV(i, j, k, u0, v1);
    
    tessellator.addVertexWithUV(i + 1.0D, j, k + 1.0D, u1, v1);
    tessellator.addVertexWithUV(i + 1.0D, j + 1.0D, k + 1.0D, u1, v0);
    tessellator.addVertexWithUV(i, j + 1.0D, k + 1.0D, u0, v0);
    tessellator.addVertexWithUV(i, j, k + 1.0D, u0, v1);
    tessellator.draw();
  }
  
  public int getRenderId() {
    return client_Sports.renderID;
  }
  
  public boolean renderWorldBlock(IBlockAccess blockAccess, int i, int j, int k, Block block, int modelId, RenderBlocks renderer) {
    tessellator = Tessellator.instance;
    boolean flag = false;
    
    int l = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
    
    IIcon icon = block.getIcon(0, 0);
    double u0 = icon.getMinU();
    double u1 = icon.getMaxU();
    double v0 = icon.getMinV();
    double v1 = icon.getMaxV();
    
    if (block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2)) {
      
      tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1));
      
      tessellator.addVertexWithUV(i, j, k, u1, v1);
      tessellator.addVertexWithUV(i, (j + 1), k, u1, v0);
      tessellator.addVertexWithUV((i + 1), (j + 1), k, u0, v0);
      tessellator.addVertexWithUV((i + 1), j, k, u0, v1);
      
      tessellator.addVertexWithUV(i, j, (k + 1), u1, v1);
      tessellator.addVertexWithUV(i, (j + 1), (k + 1), u1, v0);
      tessellator.addVertexWithUV((i + 1), (j + 1), (k + 1), u0, v0);
      tessellator.addVertexWithUV((i + 1), j, (k + 1), u0, v1);
      
      flag = true;
    } 
    if (block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3)) {
      
      tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1));
      
      tessellator.addVertexWithUV(i, j, k, u1, v1);
      tessellator.addVertexWithUV(i, (j + 1), k, u1, v0);
      tessellator.addVertexWithUV(i, (j + 1), (k + 1), u0, v0);
      tessellator.addVertexWithUV(i, j, (k + 1), u0, v1);
      
      tessellator.addVertexWithUV((i + 1), j, k, u1, v1);
      tessellator.addVertexWithUV((i + 1), (j + 1), k, u1, v0);
      tessellator.addVertexWithUV((i + 1), (j + 1), (k + 1), u0, v0);
      tessellator.addVertexWithUV((i + 1), j, (k + 1), u0, v1);
      
      flag = true;
    } 
    if (block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4)) {
      
      tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k));
      
      tessellator.addVertexWithUV(i, j, (k + 1), u1, v1);
      tessellator.addVertexWithUV(i, (j + 1), (k + 1), u1, v0);
      tessellator.addVertexWithUV(i, (j + 1), k, u0, v0);
      tessellator.addVertexWithUV(i, j, k, u0, v1);
      
      tessellator.addVertexWithUV((i + 1), j, (k + 1), u1, v1);
      tessellator.addVertexWithUV((i + 1), (j + 1), (k + 1), u1, v0);
      tessellator.addVertexWithUV((i + 1), (j + 1), k, u0, v0);
      tessellator.addVertexWithUV((i + 1), j, k, u0, v1);
      
      flag = true;
    } 
    if (block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5)) {
      
      tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k));
      
      tessellator.addVertexWithUV((i + 1), j, k, u1, v1);
      tessellator.addVertexWithUV((i + 1), (j + 1), k, u1, v0);
      tessellator.addVertexWithUV(i, (j + 1), k, u0, v0);
      tessellator.addVertexWithUV(i, j, k, u0, v1);
      
      tessellator.addVertexWithUV((i + 1), j, (k + 1), u1, v1);
      tessellator.addVertexWithUV((i + 1), (j + 1), (k + 1), u1, v0);
      tessellator.addVertexWithUV(i, (j + 1), (k + 1), u0, v0);
      tessellator.addVertexWithUV(i, j, (k + 1), u0, v1);
      
      flag = true;
    } 
    return flag;
  }

  public boolean shouldRender3DInInventory(int modelId) {
    return false;
  }
}