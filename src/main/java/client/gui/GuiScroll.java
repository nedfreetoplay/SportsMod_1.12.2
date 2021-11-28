package net.sports.client.gui;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.sports.client.settings.Settings;
import org.lwjgl.opengl.GL11;

public class GuiScroll extends GuiSlot
{
  private GuiID ID;
  private GameSettings options;
  private Minecraft mc;
  private String[] message;
  private int _mouseX;
  private int _mouseY;
  private int selected = -1;
  
  private Iterator itValue = Settings.IDs.values().iterator();
  private Iterator<String> itKey = Settings.IDs.keySet().iterator();
  private String[] keys;
  private int j = 0;

  
  public GuiScroll(GuiID ID, GameSettings options, Minecraft mc) {
    super(mc, ID.width, ID.height, 16, ID.height - 32 + 4, 25);
    this.ID = ID;
    this.options = options;
    this.mc = mc;
  }
  
  protected int getSize() {
    return Settings.IDs.size();
  }
  
  protected void elementClicked(int i, boolean flag) {
    if (!flag)
    {
      if (this.selected == -1)
      {
        this.selected = i;
      }
    }
  }

  
  protected boolean isSelected(int i) {
    return false;
  }

  
  protected void drawBackground() {}
  
  public void drawScreen(int mX, int mY, float f) {
    this.j = 0;
    this._mouseX = mX;
    this._mouseY = mY;
    this.itValue = Settings.IDs.values().iterator();
    this.itKey = Settings.IDs.keySet().iterator();
    int k = -1;
    this.keys = new String[Settings.IDs.size()];
    while (this.itKey.hasNext())
    {
      this.keys[++k] = this.itKey.next();
    }
    super.drawScreen(mX, mY, f);
    this.ID.draw(mX, mY, f);
  }


  
  protected void drawSlot(int index, int xPosition, int yPosition, int l, Tessellator tessellator, int i, int j) {
    int width = 70;
    int height = 20;
    xPosition -= 20;
    boolean flag = (this._mouseX >= xPosition && this._mouseY >= yPosition && this._mouseX < xPosition + width && this._mouseY < yPosition + height);
    int k = flag ? 2 : 1;

    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int y = yPosition + height - 12;
    this.ID.drawString(this.ID.getFontRenderer(), this.keys[index], this.ID.width / 2 - 130, y, 10526880);
    (Settings.textFields[index]).yPos = y - 5;
    Settings.textFields[index].drawTextBox();
  }
  
  protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
}