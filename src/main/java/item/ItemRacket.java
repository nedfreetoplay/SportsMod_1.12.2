package net.sports.item;

import api.player.server.IServerPlayer;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.Anim;
import net.sports.Reference;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.entity.EntitySportsmanServer;
import net.sports.entity.EntityTennis;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketInHand;
import net.sports.register.RegisterItems;

public class ItemRacket extends Item {

  public ItemRacket(String name) {
    this.maxStackSize = 1;
    this.setCreativeTab(SportsMod.tabSports);
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    RegisterItems.ITEMS.add(this);
  }

  @SideOnly(Side.CLIENT)
  public boolean isFull3D() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public EnumAction getItemUseAction(ItemStack itemstack) {
    client_Sports.hud.setBarre(2);
    return EnumAction.NONE;
  }

  public int getMaxItemUseDuration(ItemStack itemstack) {
    return 72000;
  }

  //return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    playerIn.setActiveHand(handIn);
    if (worldIn.isRemote) {
      
      client_Sports.sportsman.setAnim(Anim.ARMER_TIR);
      client_Sports.sportsman.setSport(Sports.TENNIS);
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
  }
  
  public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
    if (world.isRemote) {
      
      client_Sports.sportsman.setAnim(Anim.RESET);
      return;
    }
    List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)entityplayer, entityplayer.getEntityBoundingBox().expand(1.9000000029802322D, 1.9D, 1.9000000029802322D));
    if (list != null && list.size() > 0)
    {
      for (int i1 = 0; i1 < list.size(); i1++) {
        
        Entity entity = list.get(i1);
        if (entity instanceof EntityTennis) {
          
          //if (!world.isRemote) world.playSoundAtEntity((Entity)entityplayer, "sports:tennisshoot", 0.4F, 1.0F);
          if(!world.isRemote)
            world.playSound(entityplayer, entityplayer.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID + ":tennisshoot")), SoundCategory.PLAYERS, 0.4F, 1.0F);
          EntityTennis tennis = (EntityTennis)entity;
          tennis.rotationPitch = entityplayer.rotationPitch - 25.0F;
          tennis.rotationYaw = entityplayer.rotationYaw;
          tennis.tir((2 + getMaxItemUseDuration(itemstack) - i));
          EntityPlayerMP entityplay = (EntityPlayerMP)entityplayer;
          EntitySportsmanServer player = (EntitySportsmanServer)((IServerPlayer)entityplayer).getServerPlayerBase("EntitySportsmanServer");
          player.play = 0;
          tennis.sportsmanServer = null;
          tennis.sportsman = null;
          tennis.inhand = false;
          tennis.player = (EntityPlayerMP)entityplayer;
          SportsMod.packetPipeline.sendToAll((AbstractPacket)new PacketInHand(tennis.hashCode(), tennis.player.hashCode(), tennis.inhand));
        } 
      } 
    }
  }
}