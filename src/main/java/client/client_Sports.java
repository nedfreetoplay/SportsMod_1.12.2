package net.sports.client;

import api.player.client.ClientPlayerAPI;
import api.player.client.IClientPlayer;
import api.player.model.ModelPlayerAPI;
import api.player.render.RenderPlayerAPI;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.Timer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.sports.SportsMod;
import net.sports.client.entity.EntitySportsman;
import net.sports.client.gui.Hud;
import net.sports.client.model.ModelSportsman;
import net.sports.client.render.RenderBall;
import net.sports.client.render.RenderBaseballGlove;
import net.sports.client.render.RenderBlock;
import net.sports.client.render.RenderGlove;
import net.sports.client.render.RenderSportsman;
import net.sports.client.settings.GetPrivateAccess;
import net.sports.client.settings.KeyBindings;
import net.sports.client.settings.KeysHandler;
import net.sports.client.settings.Settings;
import net.sports.common_Sports;
import net.sports.entity.EntityBall;
import net.sports.entity.EntityBaseball;
import net.sports.entity.EntityBasket;
import net.sports.entity.EntitySoccer;
import net.sports.entity.EntityTennis;

public class client_Sports extends common_Sports
{
  public static Settings Settings;
  public static GetPrivateAccess privateAccess;
  public static boolean init = true;
  protected boolean sA = false;
  public static int renderID;
  public static Hud hud = new Hud();
  protected Timer timer = new Timer(100.0F);

  public static EntitySportsman sportsman;

  public void registerRenderInformation() {
    Settings = new Settings(Minecraft.getMinecraft());
    Settings.loadOptions();
    privateAccess = new GetPrivateAccess(Minecraft.getMinecraft(), Settings);
    
    for (int i = 0; i < 8; i++) {
      MinecraftForgeClient.registerItemRenderer(SportsMod.glove[i], (IItemRenderer)new RenderGlove(SportsMod.gcolor[i]));
    }
    
    MinecraftForgeClient.registerItemRenderer(SportsMod.baseballGlove, (IItemRenderer)new RenderBaseballGlove());
    
    FMLCommonHandler.instance().bus().register(new KeysHandler());
    
    KeyBindings.init();
    
    RenderingRegistry.registerEntityRenderingHandler(EntitySoccer.class, (Render)new RenderBall("football", 0.55F, 0.4F, 0.001F));
    RenderingRegistry.registerEntityRenderingHandler(EntityBasket.class, (Render)new RenderBall("basketball", 0.65F, 0.5F, 0.0011F));
    RenderingRegistry.registerEntityRenderingHandler(EntityTennis.class, (Render)new RenderBall("tennis", 0.25F, 0.2F, 4.0E-4F));
    RenderingRegistry.registerEntityRenderingHandler(EntityBaseball.class, (Render)new RenderBall("baseball", 0.25F, 0.2F, 4.0E-4F));
    
    renderID = RenderingRegistry.getNextAvailableRenderId();
    RenderingRegistry.registerBlockHandler(renderID, (ISimpleBlockRenderingHandler)new RenderBlock());
    
    ClientPlayerAPI.register("EntitySportsman", EntitySportsman.class);
    ModelPlayerAPI.register("ModelSportsman", ModelSportsman.class);
    RenderPlayerAPI.register("RenderSportsman", RenderSportsman.class);
  }

  public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen gui) {
    if (gui instanceof net.minecraft.client.gui.GuiOptions) {
      
      GetPrivateAccess tmp17_14 = privateAccess;
      tmp17_14.getClass();
      Minecraft.getMinecraft().displayGuiScreen(gui);
    } 
    return true;
  }
  
  public static double limite(double target, double limit) {
    if (target < -limit)
    {
      target = -limit;
    }
    if (target > limit)
    {
      target = limit;
    }
    
    return target;
  }
  
  public void onUpdate(EntityBall entity) {
    entity.onUpdateClient();
    entity.onUpdateServer();
  }
  
  public void tick(TickEvent event) {
    if (event.type == TickEvent.Type.RENDER) {
      
      onRenderTick();
    }
    else if (event.type == TickEvent.Type.CLIENT) {
      
      GuiScreen guiscreen = (Minecraft.getMinecraft()).currentScreen;
      if (guiscreen != null) {
        
        onTickInGUI(guiscreen);
      } else {
        
        onTickInGame();
      } 
      if (sportsman == null) {
        
        EntityClientPlayerMP playerSP = (Minecraft.getMinecraft()).thePlayer;
        if (playerSP != null)
          sportsman = (EntitySportsman)((IClientPlayer)playerSP).getClientPlayerBase("EntitySportsman"); 
      } 
    } 
  }
  
  public void onRenderTick() {
    hud.draw();
    
    this.timer.updateTimer();
    if (this.timer.elapsedTicks == 0)
      return; 
    hud.onUpdate();
  }
  
  public void onTickInGUI(GuiScreen gui) {
    if (gui instanceof net.minecraft.client.gui.GuiOptions)
    {

      
      Minecraft.getMinecraft().displayGuiScreen(gui);
    }
  }
  
  public void onTickInGame() {}
}