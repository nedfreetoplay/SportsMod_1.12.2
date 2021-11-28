package net.sports.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.sports.SportsMod;
import net.sports.client.model.ModelBaseballGlove;
import org.lwjgl.opengl.GL11;

public class RenderBaseballGlove implements IItemRenderer
{
  public ModelBaseballGlove glove = new ModelBaseballGlove();

  
  public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
    if (type == IItemRenderer.ItemRenderType.INVENTORY) {
      
      IIcon icon = SportsMod.baseballGlove.getIconFromDamage(0);
      float var3 = icon.getMinU();
      float var4 = icon.getMaxU();
      float var5 = icon.getMinV();
      float var6 = icon.getMaxV();
      
      GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      float f6 = 1.0F;
      float f7 = 0.5F;
      float f8 = 0.25F;
      tessellator.addVertexWithUV((0.0F - f7), (0.0F - f8), 0.0D, var3, var6);
      tessellator.addVertexWithUV((f6 - f7), (0.0F - f8), 0.0D, var4, var6);
      tessellator.addVertexWithUV((f6 - f7), (1.0F - f8), 0.0D, var4, var5);
      tessellator.addVertexWithUV((0.0F - f7), (1.0F - f8), 0.0D, var3, var5);
      tessellator.draw();
    } 
    if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
      
      GL11.glDisable(3553);
      GL11.glDisable(3008);
      GL11.glTranslatef(1.0F, 0.5F, 1.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      GL11.glRotatef(35.0F, 0.0F, 1.0F, 0.0F);
      
      float lum = 1.0F;
      GL11.glColor4f(lum * 0.89F, lum * 0.58F, lum * 0.23F, 1.0F);
      this.glove.render((Entity)data[1], 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GL11.glEnable(3008);
      GL11.glEnable(3553);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    } 
  }
  
  public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
    return true;
  }
  
  public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
    return true;
  }
}