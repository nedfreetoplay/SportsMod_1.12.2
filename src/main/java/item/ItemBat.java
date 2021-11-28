package net.sports.item;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.Anim;
import net.sports.Reference;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.entity.EntityBaseball;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketSport;
import net.sports.register.RegisterItems;
import net.sports.utils.interfaces.IHasModel;

public class ItemBat extends Item implements IHasModel
{
  private int weaponDamage;
  
  public ItemBat(String name) {
    this.maxStackSize = 1;
    this.weaponDamage = 8;
    this.setCreativeTab(SportsMod.tabSports);
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    RegisterItems.ITEMS.add(this);
  }

  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
  {
    stack.damageItem(1, (EntityLivingBase)attacker);
    return true;
  }

  //public int getDamageVsEntity(Entity par1Entity) { return this.weaponDamage; }
  public int getDamage(ItemStack stack)
  {
    return this.weaponDamage;
  }

  @SideOnly(Side.CLIENT)
  public boolean isFull3D()
  {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public EnumAction getItemUseAction(ItemStack itemstack) {
    client_Sports.hud.setBarre(10);
    return EnumAction.NONE;
  }
  
  public int getMaxItemUseDuration(ItemStack itemstack) {
    return 72000;
  }
  
  //public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
  public void onPlayerStoppedUsing(ItemStack itemstack, World worldIn, EntityLivingBase entityLiving, int timeLeft){
    if (worldIn.isRemote) {
      
      client_Sports.sportsman.setAnim(Anim.KNOCK);
      client_Sports.sportsman.setSport(Sports.BASEBALL);
      return;
    }

    List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)entityLiving, entityLiving.getEntityBoundingBox().expand(0.9D, 0.9D, 0.9D));
    if (list != null && list.size() > 0)
    {
      for (int i1 = 0; i1 < list.size(); i1++) {
        
        Entity entity = list.get(i1);
        if (entity.canBePushed() && entity instanceof EntityBaseball) {
          
          //if (!world.isRemote) world.playSoundAtEntity((Entity)entityplayer, "sports:baseballshoot", 0.5F, 0.7F);
          if(!worldIn.isRemote) worldIn.playSound((EntityPlayer) entityLiving, entityLiving.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID + ":baseballshoot")), SoundCategory.PLAYERS, 0.5F, 0.7F);
          
          EntityBaseball baseball = (EntityBaseball)entity;
          baseball.rotationYaw = entityLiving.rotationYaw;
          float diff = (float)(entityLiving.posY + 0.38999998569488525D - baseball.posY) / 2.1F;
          if (diff >= 1.0F) diff = 0.99F; 
          if (diff <= 0.0F) diff = 0.01F; 
          float asin = (float)(Math.asin((1.0F - diff)) * 180.0D) / 3.1415927F;
          baseball.rotationPitch = entityLiving.rotationPitch + 40.0F - asin;
          baseball.tir((50 + getMaxItemUseDuration(itemstack) - timeLeft));
        
        }
        else if (entity instanceof EntityLiving) {
          
          float f = 7.0F;
          entity.motionX = -Math.sin((entityLiving.rotationYaw / 180.0F) * Math.PI) * MathHelper.cos(entityLiving.rotationPitch / 180.0F * 3.1415927F) * f;
          entity.motionZ = (MathHelper.cos(entityLiving.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(entityLiving.rotationPitch / 180.0F * 3.1415927F) * f);
          entity.motionY = (-MathHelper.sin(entityLiving.rotationPitch / 180.0F * 3.1415927F) * 3.0F);
        } 
      } 
    }
  }
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
  {
    playerIn.setActiveHand(handIn);
    if (worldIn.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    SportsMod.packetPipeline.sendToServer((AbstractPacket)new PacketSport(playerIn.hashCode(), 4));

    return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
  }

  @Override
  public void registerModels() {

  }
}