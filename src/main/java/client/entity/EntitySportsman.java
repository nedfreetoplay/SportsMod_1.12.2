package net.sports.client.entity;

import api.player.client.ClientPlayerAPI;
import api.player.client.ClientPlayerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketKeys;

public class EntitySportsman extends ClientPlayerBase
{
  public float Cross;
  public float Circle;
  public boolean Square;
  public boolean Triangle;
  public PlayerParameter playerParameter;
  public static boolean srun = false;
  
  public EntitySportsman(ClientPlayerAPI playerapi) {
    super(playerapi);
    this.playerParameter = new PlayerParameter();
    this.playerParameter.sport = Sports.ATHLETISME;
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.Square = false;
    float yaw = 0.0F;
    boolean move = true;
    EntityPlayerSP pl = this.player;
    
    if (this.playerParameter.inHand && 
      (Minecraft.getMinecraft()).theWorld.getEntityByID(this.playerParameter.entityPlayed.getEntityId()) == null) this.playerParameter.inHand = false;

    
    if (pl.movementInput.moveForward == 0.0F && pl.movementInput.moveStrafe == 0.0F) {
      
      move = false;
      this.playerParameter.run = false;
    } 
    if (pl.movementInput.moveForward <= 0.0F && pl.movementInput.moveForward < 0.0F)
      yaw = 180.0F; 
    if (pl.movementInput.moveStrafe > 0.0F) { yaw = -90.0F; }
    else if (pl.movementInput.moveStrafe < 0.0F) { yaw = 90.0F; }
     if (pl.movementInput.moveForward > 0.0F && pl.movementInput.moveStrafe < 0.0F) yaw = 45.0F; 
    if (pl.movementInput.moveForward > 0.0F && pl.movementInput.moveStrafe > 0.0F) yaw = -45.0F; 
    if (pl.movementInput.moveForward < 0.0F && pl.movementInput.moveStrafe < 0.0F) yaw = 135.0F; 
    if (pl.movementInput.moveForward < 0.0F && pl.movementInput.moveStrafe > 0.0F) yaw = -135.0F; 
    if (move) {
      
      if (this.player.inventory.getCurrentItem() != null) {
        Item item = this.player.inventory.getCurrentItem().getItem();
        if (item == SportsMod.racket || SportsMod.checkGlove(item) || item == SportsMod.baseballGlove || item == SportsMod.bat) {
          
          float f = 0.2F;
          this.player.motionX = (-MathHelper.sin((this.player.rotationYaw + yaw) / 180.0F * 3.1415927F) * MathHelper.cos(this.player.rotationPitch / 180.0F * 3.1415927F) * f);
          this.player.motionZ = (MathHelper.cos((this.player.rotationYaw + yaw) / 180.0F * 3.1415927F) * MathHelper.cos(this.player.rotationPitch / 180.0F * 3.1415927F) * f);
        } 
      } 
      if (this.Triangle) {
        
        float f = this.playerParameter.maxSpeed;
        this.playerParameter.run = true;
        
        if (!srun) {
          SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketKeys(3, 1.0F));
          srun = true;
        } 
        
        this.player.motionX = (-MathHelper.sin((this.player.rotationYaw + yaw) / 180.0F * 3.1415927F) * MathHelper.cos(this.player.rotationPitch / 180.0F * 3.1415927F) * f);
        this.player.motionZ = (MathHelper.cos((this.player.rotationYaw + yaw) / 180.0F * 3.1415927F) * MathHelper.cos(this.player.rotationPitch / 180.0F * 3.1415927F) * f);
      } else {
        
        this.playerParameter.run = false;
        
        if (srun) {
          SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketKeys(3, 0.0F));
          srun = false;
        } 
      } 
    } 
  }
  
  public void resetKeys() {
    this.Cross = 0.0F;
    this.Circle = 0.0F;
  }
  
  public float getRotationPitch() {
    return this.player.rotationPitch;
  }
  
  public float getRotationYaw() {
    return this.player.rotationYaw;
  }
  
  public void setAnim(Anim anim) {
    this.playerParameter.setAnim(anim);
  }
  
  public Anim getAnim() {
    return this.playerParameter.anim;
  }
  
  public void setSport(Sports sport) {
    this.playerParameter.setSport(sport);
  }
}