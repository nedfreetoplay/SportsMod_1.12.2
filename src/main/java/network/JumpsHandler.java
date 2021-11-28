package net.sports.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sports.common_Sports;

public class JumpsHandler
{
  @SubscribeEvent
  public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
    if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer) {
      
      event.getEntity().motionY = 0.0D;
      
      EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
      if (player == null)
        return; 
      String name = player.getName();
      if (((Integer)common_Sports.endurp.get(name)).intValue() - 400 >= 0) {
        common_Sports.endurp.put(name, Integer.valueOf(((Integer)common_Sports.endurp.get(name)).intValue() - 400));
      } else {
        common_Sports.endurp.put(name, Integer.valueOf(0));
      } 
    } 
  }
}