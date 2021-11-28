package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.sports.client.settings.Settings;

public class PacketCheckID extends AbstractPacket
{
  private int[] dataInt = new int[Settings.IDs.size()];

  public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    for (int i = 0; i < Settings.IDs.size(); i++)
    {
      buffer.writeInt(this.dataInt[i]);
    }
  }

  public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    for (int i = 0; i < Settings.IDs.size(); i++)
    {
      this.dataInt[i] = buffer.readInt();
    }
  }

  public void handleClientSide(EntityPlayer player) {
    boolean update = false;
    
    Iterator<Integer> itValue = Settings.IDs.values().iterator();
    Iterator<String> itKey = Settings.IDs.keySet().iterator();
    
    for (int i = 0; i < this.dataInt.length; i++) {
      
      if (this.dataInt[i] != ((Integer)itValue.next()).intValue()) update = true; 
    } 
  }
  
  public void handleServerSide(EntityPlayer player) {}
}