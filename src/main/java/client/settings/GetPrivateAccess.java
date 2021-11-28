package net.sports.client.settings;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.sports.client.gui.GuiMenu;
import net.sports.client.gui.GuiSmallButton2;

public class GetPrivateAccess
{
  List my;
  GuiScreen myscreen;
  private final Class PARENT_option;
  private final Class PARENT_Screen;
  Minecraft min;
  Settings Settings;
  
  public GetPrivateAccess(Minecraft mc, Settings settings) {
    this.PARENT_option = GuiOptions.class;
    this.PARENT_Screen = GuiScreen.class;
    this.min = mc;
    this.Settings = settings;
  }
  
  public static GuiScreen get(GuiScreen gui) {
    if (gui instanceof GuiOptions);
    return gui;
  }
  
  public class GuiOptions2
    extends GuiOptions {
    public GuiOptions2(GuiScreen gui, Settings settings) {
      super(gui, (Minecraft.getMinecraft()).gameSettings);
      this.gui = gui;
      this.Settings = settings;
    }

    GuiScreen gui;
    
    GuiScreen parentScreen;
    Settings Settings;
    
    public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
    }

    public void initGui() {
      super.initGui();
      int var2 = 6;
      GuiSmallButton2 op = new GuiSmallButton2(154, this.width / 2 - 155, this.height / 6 + 48 - 6, "Sports mod Options");
      this.buttonList.add(op);
      ((GuiButton)this.buttonList.get(1)).enabled = false;
    }

    protected void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      if (guibutton.id == 154) {
        
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMenu((Minecraft.getMinecraft()).currentScreen, this.Settings));
        
        return;
      } 
      if (guibutton.id == 200) {
        
        (Minecraft.getMinecraft()).gameSettings.saveOptions();
        Minecraft.getMinecraft().displayGuiScreen(null);
      } 
    }
  }
}