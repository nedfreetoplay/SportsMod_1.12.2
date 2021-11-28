package net.sports.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.sports.client.settings.Options;
import org.lwjgl.opengl.GL11;

public class GuiSmallButton2
  extends GuiButton {
  private final Options enumOptions;
  
  public GuiSmallButton2(int i, int j, int k, String s) {
    this(i, j, k, (Options)null, s);
  }

  
  public GuiSmallButton2(int i, int j, int k, int l, int i1, String s) {
    super(i, j, k, l, i1, s);
    this.enumOptions = null;
  }

  
  public GuiSmallButton2(int i, int j, int k, Options enumoptions, String s) {
    super(i, j, k, 150, 20, s);
    this.enumOptions = enumoptions;
  }
  
  public void resize(int i, int j) {
    this.width = i;
    this.height = j;
  }
  
  public Options returnEnumOptions() {
    return this.enumOptions;
  }
  
  public void drawButton(Minecraft minecraft, int i, int j) {
    if (!this.enabled) {
      return;
    }
    
    FontRenderer fontrenderer = minecraft.fontRenderer;

    GL11.glDisable(3553);
    boolean flag = (i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height);
    int k = getHoverState(flag);
    drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
    drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
    
    float hg = 0.1F;
    if (flag) hg = 0.6F; 
    GL11.glShadeModel(7425);
    
    GL11.glBegin(7);
    GL11.glColor4f(0.1F, 0.1F, 0.1F, 0.9F);
    GL11.glVertex2d(this.xPosition, (this.yPosition + this.height));
    GL11.glColor4f(hg, hg, hg, 0.9F);
    GL11.glVertex2d((this.xPosition + this.width), (this.yPosition + this.height));
    GL11.glColor4f(0.4F, 0.4F, 0.4F, 0.9F);
    GL11.glVertex2d((this.xPosition + this.width), this.yPosition);
    GL11.glColor4f(0.1F, 0.1F, 0.1F, 0.9F);
    GL11.glVertex2d(this.xPosition, this.yPosition);
    GL11.glEnd();
    GL11.glEnable(3553);
    mouseDragged(minecraft, i, j);
    if (!this.enabled) {
      
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
    }
    else if (flag) {
      
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
    } else {
      
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
    } 
  }
}