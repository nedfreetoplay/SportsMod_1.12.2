package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.sports.SportsMod;
import net.sports.client.entity.PlayerParameter;
import net.sports.entity.EntityBall;

public class PacketInHand extends AbstractPacket
{
  private int entityID;
  private int playerID;
  private boolean inHand;
  
  public PacketInHand() {}
  
  public PacketInHand(int entityID, int playerID, boolean inHand) {
    this.entityID = entityID;
    this.playerID = playerID;
    this.inHand = inHand;
  }

  
  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    buffer.writeInt(this.entityID);
    buffer.writeInt(this.playerID);
    buffer.writeBoolean(this.inHand);
  }

  
  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    this.entityID = buffer.readInt();
    this.playerID = buffer.readInt();
    this.inHand = buffer.readBoolean();
  }

  
  public void handleClientSide(EntityPlayer player) {
    Entity e = player.world.getEntityByID(this.entityID);
    Entity p = player.world.getEntityByID(this.playerID);
    PlayerParameter playerParameter = PlayerParameter.getParameter(p, "EntitySportsman");
    if (e instanceof EntityBall) {
      
      EntityBall b = (EntityBall)e;
      if (this.inHand) {
        
        playerParameter.inHand = true;
        if (p == null) { b.eplayer = player; }
        else { b.eplayer = (EntityPlayer)p; }
         b.inhand = true;
        playerParameter.entityPlayed = b;
      }
      else {
        
        playerParameter.play = -1;
        playerParameter.inHand = false;
        b.eplayer = null;
        b.inhand = false;
      } 
    } 
  }

  
  public void handleServerSide(EntityPlayer player) {
    SportsMod.packetPipeline.sendToAll(new PacketInHand(this.entityID, player.getEntityId(), this.inHand));
  }
}