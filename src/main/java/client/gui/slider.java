package net.sports.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.sports.client.settings.Options;
import net.sports.client.settings.Settings;
import org.lwjgl.opengl.GL11;

public class slider extends GuiButton {
  Settings Settings;
  public float sliderValue;
  
  public slider(int i, int j, int k, Options enumoptions, String s, float f, Settings settings) {
    super(i, j, k, 150, 20, s);
    this.sliderValue = 1.0F;
    this.dragging = false;
    this.idFloat = null;
    this.idFloat = enumoptions;
    this.sliderValue = f;
    this.Settings = settings;
  }
  public boolean dragging; private Options idFloat;
  
  public int getHoverState(boolean flag) {
    return 0;
  }

  
  protected void mouseDragged(Minecraft minecraft, int i, int j) {
    if (!this.enabled) {
      return;
    }

    
    if (this.dragging) {
      
      this.sliderValue = ((i - this.xPosition + 4) / (this.width - 8));
      if (this.sliderValue < 0.0F)
      {
        this.sliderValue = 0.0F;
      }
      if (this.sliderValue > 1.0F)
      {
        this.sliderValue = 1.0F;
      }
      this.Settings.setOptionFloatValue(this.idFloat, this.sliderValue);
      this.displayString = this.Settings.getKeyBinding(this.idFloat);
    } 
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
    drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
  }

  
  public boolean mousePressed(Minecraft minecraft, int i, int j) {
    if (super.mousePressed(minecraft, i, j)) {
      
      this.sliderValue = ((i - this.xPosition + 4) / (this.width - 8));
      if (this.sliderValue < 0.0F)
      {
        this.sliderValue = 0.0F;
      }
      if (this.sliderValue > 1.0F)
      {
        this.sliderValue = 1.0F;
      }
      this.Settings.setOptionFloatValue(this.idFloat, this.sliderValue);
      this.displayString = this.Settings.getKeyBinding(this.idFloat);
      this.dragging = true;
      return true;
    } 
    
    return false;
  }


  
  public void mouseReleased(int i, int j) {
    this.dragging = false;
  }
}