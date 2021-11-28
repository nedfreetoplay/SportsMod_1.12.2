package net.sports;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.sports.client.render.RenderGlove;
import net.sports.entity.EntityBall;
import net.sports.entity.EntitySportsmanServer;
import net.sports.network.AbstractPacket;
import net.sports.network.JumpsHandler;
import net.sports.network.PacketEndurance;

public class common_Sports
{
  public void registerRenderInformation() {
    register_event(new JumpsHandler());
  }
  
  public static HashMap<String, Integer> endurp = new HashMap<String, Integer>();
  
  public static HashMap<String, Integer> keycr = new HashMap<String, Integer>();
  public static HashMap<String, Integer> keyci = new HashMap<String, Integer>();
  
  public static int ticks;

  
  public void onUpdate(EntityBall entity) {
    entity.onUpdateServer();
  }

  
  public void tick(TickEvent event) {
    for (Map.Entry<String, Integer> kv : keycr.entrySet()) {
      if (kv.getValue() != null && kv.getKey() != null && (
        (Integer)kv.getValue()).intValue() + 20 < ticks) {
        
        EntityPlayerMP epmp = (EntityPlayerMP)MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(kv.getKey());
        if (epmp == null)
          continue;  EntitySportsmanServer sportsman = (EntitySportsmanServer)((IServerPlayer)epmp).getServerPlayerBase("EntitySportsmanServer");
        sportsman.Cross = 0.0F;
        
        keycr.put(kv.getKey(), null);
      } 
    } 
    
    for (Map.Entry<String, Integer> kv : keyci.entrySet()) {
      if (kv.getValue() != null && kv.getKey() != null && (
        (Integer)kv.getValue()).intValue() + 20 < ticks) {
        
        EntityPlayerMP epmp = (EntityPlayerMP)MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(kv.getKey());
        if (epmp == null)
          continue;  EntitySportsmanServer sportsman = (EntitySportsmanServer)((IServerPlayer)epmp).getServerPlayerBase("EntitySportsmanServer");
        sportsman.Circle = 0.0F;
        
        keyci.put(kv.getKey(), null);
      } 
    } 
    
    if (event.type == TickEvent.Type.SERVER) {
      for (Object o : (MinecraftServer.getServer().getEntityWorld()).playerEntities) {
        EntityPlayerMP p = (EntityPlayerMP)o;
        EntitySportsmanServer sportsman = (EntitySportsmanServer)((IServerPlayer)p).getServerPlayerBase("EntitySportsmanServer");
        
        String name = p.getCommandSenderName();
        if (endurp.get(name) == null) {
          endurp.put(name, Integer.valueOf(6000));
        }
        
        if (sportsman.Triangle) {
          if (((Integer)endurp.get(name)).intValue() > 3) {
            endurp.put(name, Integer.valueOf(((Integer)endurp.get(name)).intValue() - 3));
          }
        } else {
          endurp.put(name, Integer.valueOf(((Integer)endurp.get(name)).intValue() + 3));
        } 
        
        if (((Integer)endurp.get(name)).intValue() > 6000) endurp.put(name, Integer.valueOf(6000));
        
        if (ticks % 20 == 0 && (
          (Integer)endurp.get(name)).intValue() != 6000) {
          SportsMod.packetPipeline.sendTo((AbstractPacket)new PacketEndurance(((Integer)endurp.get(name)).intValue()), p);
        }
      } 
      
      ticks++;
    } 
  }

  public static void reg_model() {
    for (int i = 0; i < 9; i++) {
      MinecraftForgeClient.registerItemRenderer(SportsMod.new_glove[i], (IItemRenderer)new RenderGlove(SportsMod.gcolor[i]));
    }
  }

  
  public static void register_event(Object obj) {
    FMLCommonHandler.instance().bus().register(obj);
    MinecraftForge.EVENT_BUS.register(obj);
  }
}