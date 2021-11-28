package net.sports.item;

import api.player.server.IServerPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.sports.Anim;
import net.sports.Reference;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.entity.EntityBaseball;
import net.sports.entity.EntitySportsmanServer;
import net.sports.register.RegisterItems;

import java.util.List;

public class ItemBaseballGlove extends Item {
  
  public ItemBaseballGlove(String name) {
    this.maxStackSize = 1;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    setCreativeTab(SportsMod.tabSports);
    RegisterItems.ITEMS.add(this);
  }
  //public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

    //Получить itemstack из руки
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    if (worldIn.isRemote) {

      client_Sports.sportsman.setAnim(Anim.CATCH);

      client_Sports.sportsman.setSport(Sports.BASEBALL);

      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    //playerIn.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
    playerIn.setActiveHand(handIn);
    //boundingBox -> getEntityBoundingBox
    List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn.getEntityBoundingBox().expand(1.7D, 1.5D, 1.7D));

    if (list != null && list.size() > 0) {

      EntityPlayerMP entityPlayerMP = (EntityPlayerMP)playerIn;

      EntitySportsmanServer entitySportsmanServer = (EntitySportsmanServer)((IServerPlayer)entityPlayerMP).getServerPlayerBase("EntitySportsmanServer");

      for (int i1 = 0; i1 < list.size(); ) {

        if (entitySportsmanServer.play != 0) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

        Entity entity = list.get(i1);

        if (entity instanceof EntityBaseball) {

          //worldIn.playSoundAtEntity((Entity)playerIn, "sports:baseballcatch", 0.5F, 1.0F);
          worldIn.playSound(playerIn, playerIn.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID + ":baseballcatch")), SoundCategory.PLAYERS, 0.5F, 1.0F);

          EntityBaseball entityBaseball = (EntityBaseball)entity;

          entityBaseball.onCollideWithPlayer(playerIn);

          entityBaseball.getInHand();
        }

        i1++;
      }
    }

    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
  }
  
  public EnumAction getItemUseAction(ItemStack itemstack) {
    return EnumAction.BLOCK;
  }

  public int getMaxItemUseDuration(ItemStack stack)
  {
    return 72000;
  }
}