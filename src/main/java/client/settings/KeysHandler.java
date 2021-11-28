package net.sports.client.settings;

import api.player.client.IClientPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.sports.Anim;
import net.sports.SportsMod;
import net.sports.client.entity.EntitySportsman;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketKeys;

public class KeysHandler
{
  public EntityClientPlayerMP playerSP;
  public EntitySportsman sportsman;
  
  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    this.playerSP = (Minecraft.getMinecraft()).thePlayer;
    if (this.playerSP == null)
      return;  this.sportsman = (EntitySportsman)((IClientPlayer)this.playerSP).getClientPlayerBase("EntitySportsman");
    
    if (KeyBindings.key_Cross.isPressed() && this.playerSP.movementInput.sneak) {
      
      this.sportsman.setAnim(Anim.SPINNING);
    }
    else if (KeyBindings.key_Cross.isPressed()) {
      
      this.sportsman.setAnim(Anim.ARMER_PASSE);
    } 
    if (KeyBindings.key_Circle.isPressed())
    {
      this.sportsman.setAnim(Anim.ARMER_TIR);
    }
    if (KeyBindings.key_Triangle.isPressed())
    {
      this.sportsman.Triangle = true;
    }
    if (KeyBindings.key_Square.isPressed()) {
      
      this.sportsman.Square = true;
      SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketKeys(4, 2.0F));
    } 
  }
}