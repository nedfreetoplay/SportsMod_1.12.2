package net.sports.item;

import api.player.server.IServerPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.entity.EntitySoccer;
import net.sports.entity.EntitySportsmanServer;
import net.sports.register.RegisterItems;

import java.util.List;

public class ItemGlove extends Item {

  public ItemGlove(String name) {
    this.maxStackSize = 1;
    this.setCreativeTab(SportsMod.tabSports);
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    RegisterItems.ITEMS.add(this);
  }

  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    if (worldIn.isRemote) {

      client_Sports.sportsman.setAnim(Anim.ARRET);

      client_Sports.sportsman.setSport(Sports.FOOTBALL);

      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    playerIn.setActiveHand(handIn);

    List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn.getEntityBoundingBox().expand(1.7D, 1.5D, 1.7D));

    if (list != null && list.size() > 0) {

      EntityPlayerMP entityPlayerMP = (EntityPlayerMP)playerIn;

      EntitySportsmanServer entitySportsmanServer = (EntitySportsmanServer)((IServerPlayer)entityPlayerMP).getServerPlayerBase("EntitySportsmanServer");

      for (int i1 = 0; i1 < list.size(); ) {

        if (entitySportsmanServer.play != 0) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

        Entity entity = list.get(i1);

        if (entity instanceof EntitySoccer) {

          EntitySoccer entitySoccer = (EntitySoccer)entity;

          entitySoccer.onCollideWithPlayer(playerIn);

          entitySoccer.getInHand();
        }

        i1++;
      }
    }

    return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
  }

  public EnumAction getItemUseAction(ItemStack itemstack) {
    return EnumAction.BLOCK;
  }
  
  public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    return itemstack;
  }
  
  public int getMaxItemUseDuration(ItemStack itemstack) {
    return 72000;
  }
}