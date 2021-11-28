package net.sports.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.sports.client.settings.Options;
import net.sports.client.settings.Settings;

public class GuiMenu extends GuiScreen
{
  private GuiScreen parentGuiScreen;
  protected String titre;
  private Settings guiGameSettings;
  
  public GuiMenu(GuiScreen guiscreen, Settings settings) {
    this.titre = "Sports mod Settings";
    this.guiGameSettings = settings;
    this.parentScreen = guiscreen;
  }
  
  public void initGui() {
    int i = 0;
    
    Options[] aOptions = videoOptions;
    int j = aOptions.length;
    for (int k = 0; k < j; k++) {
      
      Options options = aOptions[k];
      if (!options.getEnumFloat()) {
        
        this.buttonList.add(new GuiSmallButton2(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options, this.guiGameSettings.getKeyBinding(options)));
      } else {
        
        this.buttonList.add(new slider(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options, this.guiGameSettings.getKeyBinding(options), this.guiGameSettings.getOptionFloatValue(options), this.guiGameSettings));
      } 
      i++;
    } 
    this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 24 * i, "IDs set"));
    
    this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * ++i, "Save options"));

    
    this.guiGameSettings.loadOptions();
  }

  protected void actionPerformed(GuiButton guibutton) {
    int i = this.guiGameSettings.guiScale;
    if (guibutton.id < 100 && guibutton instanceof GuiSmallButton2) {
      
      this.guiGameSettings.setOptionValue(((GuiSmallButton2)guibutton).returnEnumOptions(), 1);
      guibutton.displayString = this.guiGameSettings.getKeyBinding(Options.getOptions(guibutton.id));
    } 
    if (guibutton.id == 200) {
      
      this.guiGameSettings.saveOptions();
      this.mc.displayGuiScreen(null);
    } 
    if (guibutton.id == 201)
    {
      this.mc.displayGuiScreen(new GuiID(this, this.guiGameSettings));
    }
    if (this.guiGameSettings.guiScale != i) {
      
      ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int j = scaledresolution.getScaledWidth();
      int k = scaledresolution.getScaledHeight();
      setWorldAndResolution(this.mc, j, k);
    } 
  }
  
  protected void keyTyped(char par1, int par2) {
    if (par1 == '\r')
    {
      actionPerformed(this.buttonList.get(0));
    }
  }

  protected void mouseClicked(int par1, int par2, int par3) {
    super.mouseClicked(par1, par2, par3);
  }

  public void drawScreen(int i, int j, float f) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, this.titre, this.width / 2, 20, 16777215);

    super.drawScreen(i, j, f);
  }

  private static Options[] videoOptions = new Options[] { Options.BALL, Options.PRECISION, Options.CROSS, Options.CIRCLE, Options.HUD };
  GuiScreen parentScreen;
}