package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.sports.client.client_Sports;

public class PacketEndurance extends AbstractPacket
{
  private int endurance;
  
  public PacketEndurance() {}
  
  public PacketEndurance(int endurance) {
    this.endurance = endurance;
  }

  
  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    buffer.writeInt(this.endurance);
  }

  
  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    this.endurance = buffer.readInt();
  }

  
  public void handleClientSide(EntityPlayer player) {
    client_Sports.hud.playerParameter.endurance = this.endurance;
  }
  
  public void handleServerSide(EntityPlayer player) {}
}