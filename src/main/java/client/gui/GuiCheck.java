package net.sports.client.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.sports.SportsMod;
import net.sports.client.settings.Settings;

public class GuiCheck extends GuiScreen
{
  int[] Data;
  protected String titre;
  
  public GuiCheck(int[] data) {
    this.Data = data;
    this.titre = "IDs Error ! Do you want apply server IDs and Quit game to apply changes ?";
  }
  
  public void initGui() {
    this.buttonList.add(new GuiSmallButton2(201, this.width / 2 - 200, this.height / 6 + 72, "Yes"));
    this.buttonList.add(new GuiSmallButton2(202, this.width / 2, this.height / 6 + 72, "No"));
  }
  
  protected void actionPerformed(GuiButton guibutton) {
    if (guibutton.id == 201) {

      
      try {
        PrintWriter printwriter = new PrintWriter(new FileWriter(new File("./mod_SportsIDs.properties")));
        Iterator itValue = Settings.IDs.values().iterator();
        Iterator<String> itKey = Settings.IDs.keySet().iterator();
        printwriter.println("Version:" + SportsMod.IDsVersion());
        for (int i = 0; i < this.Data.length; i++) {
          
          String key = itKey.next();
          printwriter.println(key + ":" + this.Data[i]);
        } 
        printwriter.close();
      }
      catch (Exception exception) {
        
        System.out.println("Failed to save options");
        exception.printStackTrace();
      } 
      this.mc.shutdown();
    } 
    if (guibutton.id == 202)
    {
      this.mc.displayGuiScreen(null);
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
    drawCenteredString(this.fontRendererObj, this.titre, this.width / 2, 80, 16777215);
    
    super.drawScreen(i, j, f);
  }
}