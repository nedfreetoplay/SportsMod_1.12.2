package net.sports.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.sports.client.settings.Settings;

public class GuiID extends GuiScreen
{
  private GuiScroll scrollPane;
  int i = 0; private GuiScreen parentGuiScreen; protected String titre;
  
  public FontRenderer getFontRenderer() {
    return this.fontRendererObj;
  }
  private Settings guiGameSettings; GuiScreen parentScreen;
  public GuiID(GuiScreen guiscreen, Settings settings) {
    this.titre = "IDs Settings";
    this.guiGameSettings = settings;
    this.parentScreen = guiscreen;
  }
  
  public void initGui() {
    this.scrollPane = new GuiScroll(this, (Minecraft.getMinecraft()).gameSettings, Minecraft.getMinecraft());
    for (int k = 0; k < Settings.textFields.length; k++)
    {
      Settings.textFields[k] = new GuiField(this.fontRendererObj, this.width / 2 - 40, 6 + 24 * ++this.i, 30, 20);
    }
    this.buttonList.add(new GuiSmallButton2(200, this.width / 2 - 160, this.height - 26, "Back"));
    this.buttonList.add(new GuiSmallButton2(201, this.width / 2 + 5, this.height - 26, "Save and Quit game"));
    this.scrollPane.registerScrollButtons(7, 8);
    this.guiGameSettings.loadOptions();
  }
  
  protected void actionPerformed(GuiButton guibutton) {
    int i = this.guiGameSettings.guiScale;
    if (guibutton.id == 200)
    {
      this.mc.displayGuiScreen(new GuiMenu(null, this.guiGameSettings));
    }
    if (guibutton.id == 201) {
      
      this.guiGameSettings.saveIDs();
      this.mc.shutdown();
    } 
    if (this.guiGameSettings.guiScale != i) {
      
      ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int j = scaledresolution.getScaledWidth();
      int k = scaledresolution.getScaledHeight();
      setWorldAndResolution(this.mc, j, k);
    } 
  }
  
  protected void keyTyped(char par1, int par2) {
    for (int k = 0; k < Settings.textFields.length; k++) {
      
      Settings.textFields[k].textboxKeyTyped(par1, par2);
      ((GuiButton)this.buttonList.get(0)).enabled = (Settings.textFields[k].getText().trim().length() > 0);
    } 
    if (par1 == '\r')
    {
      actionPerformed(this.buttonList.get(0));
    }
  }
  
  protected void mouseClicked(int par1, int par2, int par3) {
    super.mouseClicked(par1, par2, par3);
    for (int k = 0; k < Settings.textFields.length; k++)
    {
      Settings.textFields[k].mouseClicked(par1, par2, par3);
    }
  }
  
  public void drawScreen(int i, int j, float f) {
    drawDefaultBackground();
    
    this.scrollPane.drawScreen(i, j, f);
  }

  
  public void draw(int i, int j, float f) {
    drawCenteredString(this.fontRendererObj, this.titre, this.width / 2, 4, 16777215);
    
    super.drawScreen(i, j, f);
  }
}