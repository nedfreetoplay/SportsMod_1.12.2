package net.sports.network;

import api.player.server.IServerPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.sports.common_Sports;
import net.sports.entity.EntitySportsmanServer;

public class PacketKeys extends AbstractPacket
{
  private int key;
  private float value;
  
  public PacketKeys() {}
  
  public PacketKeys(int key, float value) {
    this.key = key;
    this.value = value;
  }

  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    buffer.writeInt(this.key);
    buffer.writeFloat(this.value);
  }

  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    this.key = buffer.readInt();
    this.value = buffer.readFloat();
  }

  public void handleClientSide(EntityPlayer player) {}

  public void handleServerSide(EntityPlayer player) {
    if (!(player instanceof EntityPlayerMP))
      return;  EntityPlayerMP playerMP = (EntityPlayerMP)player;
    EntitySportsmanServer sportsman = (EntitySportsmanServer)((IServerPlayer)playerMP).getServerPlayerBase("EntitySportsmanServer");
    if (sportsman == null)
      return; 
    switch (this.key) {
      case 1:
        sportsman.Cross = this.value;
        common_Sports.keycr.put(player.getName(), Integer.valueOf(common_Sports.ticks));
        break;
      case 2:
        sportsman.Circle = this.value;
        common_Sports.keyci.put(player.getName(), Integer.valueOf(common_Sports.ticks));
        break;
      case 3:
        sportsman.Triangle = (this.value == 1.0F);
        common_Sports.endurp.put(player.getName(), Integer.valueOf(((Integer)common_Sports.endurp.get(player.getName())).intValue() - 3)); break;
      case 4:
        sportsman.Square = true;
        break;
    } 
  }
}