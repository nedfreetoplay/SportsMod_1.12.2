package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.entity.PlayerParameter;

public class PacketSport extends AbstractPacket
{
  private int playerID;
  private int type;
  
  public PacketSport() {}
  
  public PacketSport(int playerID, int type) {
    this.playerID = playerID;
    this.type = type;
  }

  
  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    buffer.writeInt(this.playerID);
    buffer.writeInt(this.type);
  }

  
  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    this.playerID = buffer.readInt();
    this.type = buffer.readInt();
  }

  
  public void handleClientSide(EntityPlayer player) {
    Entity playerMP = player.world.getEntityByID(this.playerID);
    PlayerParameter playerParameter = PlayerParameter.getParameter(playerMP, "EntitySportsman");
    
    playerParameter.sport = Sports.get(this.type);
  }

  
  public void handleServerSide(EntityPlayer player) {
    SportsMod.packetPipeline.sendToAll(this);
  }
}