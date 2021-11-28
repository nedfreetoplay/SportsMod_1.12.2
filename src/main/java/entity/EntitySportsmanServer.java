package net.sports.entity;

import api.player.server.ServerPlayerAPI;
import api.player.server.ServerPlayerBase;
import net.minecraft.entity.player.EntityPlayerMP;

public class EntitySportsmanServer extends ServerPlayerBase
{
  public float Cross;
  public float Circle;
  public boolean Square;
  public boolean Triangle;
  public int play;
  public int sport;
  public int animType = -1;

  public EntitySportsmanServer(ServerPlayerAPI playerapi) {
    super(playerapi);
    this.sport = -1;
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.Square = false;
  }
  
  public float getRotationPitch() {
    return this.player.rotationPitch;
  }
  
  public float getRotationYaw() {
    return this.player.rotationYaw;
  }
  
  public EntityPlayerMP getPlayer() {
    return this.player;
  }
}