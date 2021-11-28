package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.sports.Anim;
import net.sports.SportsMod;
import net.sports.client.entity.PlayerParameter;

public class PacketEvent extends AbstractPacket
{
  private int event;
  private int hashcode;
  
  public PacketEvent() {}
  
  public PacketEvent(int event, int hashcode) {
    this.event = event;
    this.hashcode = hashcode;
  }

  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    buffer.writeInt(this.event);
    buffer.writeInt(this.hashcode);
  }

  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    this.event = buffer.readInt();
    this.hashcode = buffer.readInt();
  }

  public void handleClientSide(EntityPlayer player) {
    Entity playerMP = player.world.getEntityByID(this.hashcode);
    PlayerParameter playerParameter = PlayerParameter.getParameter(playerMP, "EntitySportsman");
    
    playerParameter.anim = Anim.get(this.event);
    playerParameter.time = 0;
  }

  public void handleServerSide(EntityPlayer player) {
    SportsMod.packetPipeline.sendToAll(this);
  }
}