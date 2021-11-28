package net.sports.client.entity;

import api.player.client.IClientPlayer;
import java.util.Hashtable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.Timer;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.entity.EntityBall;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketEvent;
import net.sports.network.PacketSport;

public class PlayerParameter
{
  public int play;
  public boolean inHand;
  public Anim anim;
  public Sports sport;
  public float maxSpeed = 0.2F;
  public int endurance = 6000;
  public boolean run = false;
  public int time = 0;
  public int animTime = 0;
  public EntityBall entityPlayed;
  public Timer timer = new Timer(10.0F);
  public Timer timerAnim = new Timer(40.0F);
  
  public float rotXActuel;
  public Anim lastAnim;
  private static Hashtable otherPlayersMP;
  public float rotateAngleX;
  
  public void reset() {
    this.anim = Anim.RESET;
    this.lastAnim = Anim.INIT;
  }
  public float rotateAngleY; public float rotateAngleZ; public float rotationPointX; public float rotationPointY; public float rotationPointZ;
  public void setAnim(Anim animType) {
    if (animType == this.lastAnim)
      return;  this.time = 0;
    this.animTime = 0;
    this.anim = animType;
    SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketEvent(this.anim.ordinal(), (Minecraft.getMinecraft()).thePlayer.hashCode()));
  }
  
  public void setSport(Sports sport) {
    if (this.sport != sport) SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketSport((Minecraft.getMinecraft()).thePlayer.hashCode(), sport.ordinal())); 
    this.sport = sport;
  }
  
  public static PlayerParameter getParameter(Entity entity, String classplayerBase) {
    if (entity == null || entity instanceof net.minecraft.client.entity.EntityPlayerSP) {
      
      EntityClientPlayerMP entityClientPlayerMP = (Minecraft.getMinecraft()).thePlayer;
      EntitySportsman player = (EntitySportsman)((IClientPlayer)entityClientPlayerMP).getClientPlayerBase("EntitySportsman");
      return player.playerParameter;
    } 
    return getPLayer(entity, classplayerBase);
  }
  
  public static PlayerParameter getPLayer(Entity entity, String classPlayerBase) {
    if (otherPlayersMP == null) otherPlayersMP = new Hashtable<Object, Object>();
    
    PlayerParameter playerBase = new PlayerParameter();
    
    if (otherPlayersMP.get(classPlayerBase) == null) {
      
      otherPlayersMP.put(classPlayerBase, new Hashtable<Object, Object>());
    }
    else {
      
      Hashtable<Integer, PlayerParameter> hashTable = (Hashtable)otherPlayersMP.get(classPlayerBase);
      if (hashTable.get(Integer.valueOf(entity.hashCode())) == null) {
        
        hashTable.put(Integer.valueOf(entity.hashCode()), playerBase);
      }
      else {
        
        return hashTable.get(Integer.valueOf(entity.hashCode()));
      } 
    } 
    return playerBase;
  }
  
  public void setRotate(float f, float f1, float f2) {
    this.rotateAngleX = f;
    this.rotateAngleY = f1;
    this.rotateAngleZ = f2;
  }
  
  public void setRotationPoint(float f, float f1, float f2) {
    this.rotationPointX = f;
    this.rotationPointY = f1;
    this.rotationPointZ = f2;
  }
}