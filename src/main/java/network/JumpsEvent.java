package net.sports.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sports.client.client_Sports;

public class JumpsEvent
{
  @SubscribeEvent
  public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
    double nm = client_Sports.hud.playerParameter.endurance / 6000.0D;
    if (nm < 0.1D) {
      (Minecraft.getMinecraft()).player.motionX *= 0.4D;
      (Minecraft.getMinecraft()).player.motionZ *= 0.4D;
      nm = 0.0D;
    } 
    if (nm + 0.1D > 0.9D) {
      nm = 0.9D;
    }
    (Minecraft.getMinecraft()).player.motionY *= nm + 0.1D;
  }
}