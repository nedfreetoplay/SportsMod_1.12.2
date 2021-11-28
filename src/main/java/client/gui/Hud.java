package net.sports.client.gui;

import api.player.client.IClientPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.client.entity.EntitySportsman;
import net.sports.client.entity.PlayerParameter;
import net.sports.client.settings.KeyBindings;
import net.sports.client.settings.Settings;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketKeys;
import org.lwjgl.opengl.GL11;

public class Hud
{
  ResourceLocation background = new ResourceLocation("sports:textures/background.png");
  ResourceLocation athletisme = new ResourceLocation("sports:textures/athletisme.png");
  ResourceLocation basketball = new ResourceLocation("sports:textures/basketball.png");
  ResourceLocation football = new ResourceLocation("sports:textures/football.png");
  ResourceLocation tennis = new ResourceLocation("sports:textures/tennis.png");
  ResourceLocation baseball = new ResourceLocation("sports:textures/baseball.png");
  ResourceLocation sports = new ResourceLocation("sports:textures/sports.png");
  GuiIngame gui;
  Minecraft mc = Minecraft.getMinecraft();
  public EntityPlayerSP playerSP;
  public EntitySportsman sportsman;
  public PlayerParameter playerParameter;
  ScaledResolution var3;
  float pow;
  int inc = 0;
  int x;
  int y;
  Timer timer = new Timer(40.0F);
  int time = 0;
  
  public static float Cross;
  public static float Circle;
  
  public void setBarre(int i) {
    this.timer.updateTimer();
    if (this.timer.elapsedTicks == 0) {
      return;
    }
    this.time++;
    this.pow += i;
    this.inc = 0;
  }

  public void onUpdate() {
    this.playerSP = (EntityPlayerSP)(Minecraft.getMinecraft()).thePlayer;
    if (this.playerSP == null) {
      return;
    }
    this.sportsman = (EntitySportsman)((IClientPlayer)this.playerSP).getClientPlayerBase("EntitySportsman");
    this.playerParameter = this.sportsman.playerParameter;
    if (!KeyBindings.key_Cross.getIsKeyPressed() || !this.playerSP.movementInput.sneak) {
      if (KeyBindings.key_Cross.getIsKeyPressed()) {
        Cross += 1.6F * (0.5F + Settings.cross);
      } else if (this.sportsman.getAnim() == Anim.SPINNING) {
        this.sportsman.setAnim(Anim.RESET);
      } 
    }
    if (KeyBindings.key_Circle.getIsKeyPressed()) {
      Circle += 1.6F * (0.5F + Settings.circle);
    }
    if (!KeyBindings.key_Triangle.getIsKeyPressed() && this.sportsman.Triangle == true) {
      this.sportsman.Triangle = false;
    }
    if (!KeyBindings.key_Cross.getIsKeyPressed() && Cross > 0.0F) {
      
      this.sportsman.Cross = Cross;
      SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketKeys(1, Cross));
      Cross = 0.0F;
      this.sportsman.setAnim(Anim.PASSE);
    } 
    if (!KeyBindings.key_Circle.getIsKeyPressed() && Circle > 0.0F) {
      
      this.sportsman.Circle = Circle;
      SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketKeys(2, Circle));
      Circle = 0.0F;
      this.sportsman.setAnim(Anim.TIR);
    } 
    this.inc++;
    String var9 = "";
    if (this.pow < Circle) {
      this.pow = Circle;
    }
    if (this.pow < Cross) {
      this.pow = Cross;
    }
    if (this.inc < 30) {
      
      var9 = "Pow : " + this.pow;
    }
    else {
      
      this.inc = 0;
      this.pow = 0.0F;
    } 
    if (this.playerParameter == null) {
      return;
    }
    
    this.playerParameter.maxSpeed = 0.2F + 0.1F * this.playerParameter.endurance / 6000.0F;
    if (this.playerParameter.endurance > 6000) {
      this.playerParameter.endurance = 6000;
    }
  }

  
  public void draw() {
    this.var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
    this.gui = (Minecraft.getMinecraft()).ingameGUI;
    this.playerSP = (EntityPlayerSP)(Minecraft.getMinecraft()).thePlayer;
    if (this.playerSP == null) {
      return;
    }
    this.sportsman = (EntitySportsman)((IClientPlayer)this.playerSP).getClientPlayerBase("EntitySportsman");
    this.playerParameter = this.sportsman.playerParameter;
    
    this.x = 2;
    this.y = this.var3.getScaledHeight() - 34;
    if (Settings.hud == 0 || Settings.hud == 1) {
      
      this.mc.renderEngine.bindTexture(this.background);
      drawTexture(this.x, this.y, 32, 32);
      if (this.playerParameter.sport == Sports.ATHLETISME) {
        this.mc.renderEngine.bindTexture(this.athletisme);
      }
      if (this.playerParameter.sport == Sports.BASKETBALL) {
        this.mc.renderEngine.bindTexture(this.basketball);
      }
      if (this.playerParameter.sport == Sports.FOOTBALL) {
        this.mc.renderEngine.bindTexture(this.football);
      }
      if (this.playerParameter.sport == Sports.TENNIS) {
        this.mc.renderEngine.bindTexture(this.tennis);
      }
      if (this.playerParameter.sport == Sports.BASEBALL) {
        this.mc.renderEngine.bindTexture(this.baseball);
      }
      drawTexture(this.x, this.y, 32, 32);
    } 
    if (Settings.hud == 0 || Settings.hud == 2) {
      
      this.mc.renderEngine.bindTexture(this.sports);
      this.gui.drawTexturedModalRect(this.x + 36, this.y + 16, 0, 10, 65, 5);
      this.gui.drawTexturedModalRect(this.x + 36, this.y + 16, 0, 15, (int)(this.playerParameter.maxSpeed * 180.0F + 10.0F), 5);
      this.gui.drawTexturedModalRect(this.x + 36, this.y + 16, 0, 20, this.playerParameter.endurance / 100, 5);
      
      this.gui.drawTexturedModalRect(this.x + 36, this.y + 26, 0, 0, 65, 5);
      this.gui.drawTexturedModalRect(this.x + 36, this.y + 26, 0, 5, (int)client_Sports.limite(this.pow, 64.0D), 5);
    } 
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }

  
  public void drawTexture(int par1, int par2, int par5, int par6) {
    float var7 = 0.00390625F;
    float var8 = 0.00390625F;
    Tessellator var9 = Tessellator.instance;
    int z = -90;
    var9.startDrawingQuads();
    var9.addVertexWithUV((par1 + 0), (par2 + par6), z, 0.0D, 1.0D);
    var9.addVertexWithUV((par1 + par5), (par2 + par6), z, 1.0D, 1.0D);
    var9.addVertexWithUV((par1 + par5), (par2 + 0), z, 1.0D, 0.0D);
    var9.addVertexWithUV((par1 + 0), (par2 + 0), z, 0.0D, 0.0D);
    var9.draw();
  }
}