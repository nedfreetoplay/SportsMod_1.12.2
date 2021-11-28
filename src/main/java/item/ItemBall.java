package net.sports.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.entity.EntityBaseball;
import net.sports.entity.EntityBasket;
import net.sports.entity.EntitySoccer;
import net.sports.entity.EntityTennis;
import net.sports.register.RegisterItems;

public class ItemBall extends Item {
  //Здесь был выбор текстуры из football, basketball, tennis, baseball через int texture
  Sports sport;

  /*
    Я тут подумал и решил создать из этого ItemBall шаблон для создание классов football, basket ball, tennis, baseball
   */
  
  public ItemBall(String name, int maxStack, Sports sport) {
    this.maxStackSize = maxStack;
    this.sport = sport;
    this.setCreativeTab(SportsMod.tabSports);
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    RegisterItems.ITEMS.add(this);
  }

  //return super.onItemRightClick(worldIn, playerIn, handIn);

  //public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    float f = 1.0F;
    float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
    float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
    double d = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
    double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + 1.62D - playerIn.yOffset;
    double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
    Vec3d vec3Start = Vec3d.createVectorHelper(d, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.01745329F);
    float f6 = MathHelper.sin(-f1 * 0.01745329F);
    float f7 = f4 * f5;
    float f8 = f6;
    float f9 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec3End = vec3Start.addVector(f7 * d3, f8 * d3, f9 * d3);
    MovingObjectPosition movingobjectposition = worldIn.rayTraceBlocks(vec3Start, vec3End, true);
    if (movingobjectposition == null)
    {
      return itemstack;
    }

    int i = movingobjectposition.blockX;
    int j = movingobjectposition.blockY;
    int k = movingobjectposition.blockZ;
    if (!worldIn.isRemote)
    {
      if (this.sport == Sports.FOOTBALL) { worldIn.spawnEntityInWorld((Entity)new EntitySoccer(world, (i + 0.5F), (j + 1.5F), (k + 0.5F))); }
      else if (this.sport == Sports.BASKETBALL) { worldIn.spawnEntityInWorld((Entity)new EntityBasket(world, (i + 0.5F), (j + 1.5F), (k + 0.5F))); }
      else if (this.sport == Sports.TENNIS) { worldIn.spawnEntityInWorld((Entity)new EntityTennis(world, (i + 0.5F), (j + 1.5F), (k + 0.5F))); }
      else if (this.sport == Sports.BASEBALL) { worldIn.spawnEntityInWorld((Entity)new EntityBaseball(world, (i + 0.5F), (j + 1.5F), (k + 0.5F))); }
       } 
    itemstack.stackSize--;
    
    return itemstack;
  }
}