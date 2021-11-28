package net.sports.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Sharable
public class PacketPipeline
  extends MessageToMessageCodec<FMLProxyPacket, AbstractPacket>
{
  private EnumMap<Side, FMLEmbeddedChannel> channels;
  private LinkedList<Class<? extends AbstractPacket>> packets = new LinkedList<Class<? extends AbstractPacket>>();

  private boolean isPostInitialised = false;

  public void registerPackets() {
    registerPacket((Class)PacketEvent.class);
    registerPacket((Class)PacketEndurance.class);
    registerPacket((Class)PacketCheckID.class);
    registerPacket((Class)PacketInHand.class);
    registerPacket((Class)PacketSport.class);
    registerPacket((Class)PacketKeys.class);
  }
  public boolean registerPacket(Class<? extends AbstractPacket> clazz) {
    if (this.packets.size() > 256)
    {
      return false;
    }
    
    if (this.packets.contains(clazz))
    {
      return false;
    }
    
    if (this.isPostInitialised)
    {
      return false;
    }
    
    this.packets.add(clazz);
    return true;
  }

  protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, List<Object> out) throws Exception {
    ByteBuf buffer = Unpooled.buffer();
    Class<? extends AbstractPacket> clazz = (Class)msg.getClass();
    if (!this.packets.contains(msg.getClass()))
    {
      throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
    }
    
    byte discriminator = (byte)this.packets.indexOf(clazz);
    buffer.writeByte(discriminator);
    msg.encodeInto(ctx, buffer);
    FMLProxyPacket proxyPacket = new FMLProxyPacket((PacketBuffer) buffer.copy(), (String)ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
    out.add(proxyPacket);
  }
  
  protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
    EntityPlayer player;
    INetHandler netHandler;
    EntityPlayerMP entityPlayerMP;
    ByteBuf payload = msg.payload();
    byte discriminator = payload.readByte();
    Class<? extends AbstractPacket> clazz = this.packets.get(discriminator);
    if (clazz == null) {
      throw new NullPointerException("No packet registered for discriminator: " + discriminator);
    }
    
    AbstractPacket pkt = clazz.newInstance();
    pkt.decodeInto(ctx, payload.slice());

    
    switch (FMLCommonHandler.instance().getEffectiveSide()) {
      
      case CLIENT:
        player = getClientPlayer();
        pkt.handleClientSide(player);
        break;
      
      case SERVER:
        netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
        entityPlayerMP = ((NetHandlerPlayServer)netHandler).player;
        pkt.handleServerSide((EntityPlayer)entityPlayerMP);
        break;
    } 

    out.add(pkt);
  }

  public void initialise() {
    this.channels = NetworkRegistry.INSTANCE.newChannel("GCA", new ChannelHandler[] { (ChannelHandler)this });
    registerPackets();
  }

  public void postInitialise() {
    if (this.isPostInitialised) {
      return;
    }

    this.isPostInitialised = true;
    Collections.sort(this.packets, new Comparator<Class<? extends AbstractPacket>>()
        {

          
          public int compare(Class<? extends AbstractPacket> clazz1, Class<? extends AbstractPacket> clazz2)
          {
            int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
            if (com == 0)
            {
              com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
            }
            
            return com;
          }
        });
  }
  
  @SideOnly(Side.CLIENT)
  private EntityPlayer getClientPlayer() {
    return (EntityPlayer)(Minecraft.getMinecraft()).player;
  }

  public void sendToAll(AbstractPacket message) {
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(message);
  }

  public void sendTo(AbstractPacket message, EntityPlayerMP player) {
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(message);
  }

  public void sendToAllAround(AbstractPacket message, NetworkRegistry.TargetPoint point) {
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(message);
  }

  public void sendToDimension(AbstractPacket message, int dimensionId) {
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(Integer.valueOf(dimensionId));
    ((FMLEmbeddedChannel)this.channels.get(Side.SERVER)).writeAndFlush(message);
  }

  public void sendToServer(AbstractPacket message) {
    ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
    ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).writeAndFlush(message);
  }
}