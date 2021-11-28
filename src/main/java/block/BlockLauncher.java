package net.sports.block;

import java.util.Random;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.SportsMod;
import net.sports.entity.EntityBaseball;
import net.sports.entity.EntitySoccer;
import net.sports.entity.EntityTennis;
import net.sports.register.RegisterBlocks;
import net.sports.register.RegisterItems;

public class BlockLauncher extends BlockDispenser {
  private Random random;
  
  public BlockLauncher(String name) {
    this.setRegistryName(name);
    this.setUnlocalizedName(name);
    this.setCreativeTab(SportsMod.tabSports);
    RegisterBlocks.BLOCKS.add(this);
    RegisterItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    
    this.random = new Random();
  }
  //int i, int j, int k -> BlockPos
  /*private void dispenseItem2(World world, BlockPos pos, Random random) {
    int l = world.getBlockMetadata(i, j, k);
    double i1 = 0.0D;
    double j1 = 0.0D;
    double y = 0.34D;
    double lower = -0.10000000149011612D;
    double higher = 0.10000000149011612D;
    double powl = 0.04D;
    double powh = 0.1D;
    if (world.getDifficulty() == EnumDifficulty.EASY) {
      
      lower = -0.20000000298023224D;
      higher = 0.20000000298023224D;
      powh = 0.14D;
      y = 0.28D;
    } 
    if (world.getDifficulty() == EnumDifficulty.NORMAL) {
      
      lower = -0.30000001192092896D;
      higher = 0.30000001192092896D;
      powh = 0.18D;
      y = 0.2D;
    } 
    if (world.getDifficulty() == EnumDifficulty.HARD) {
      
      lower = -0.4000000059604645D;
      higher = 0.4000000059604645D;
      powh = 0.24D;
      y = 0.13D;
    } 
    double rand = Math.random() * (higher - lower) + lower;
    double power = Math.random() * (powh - powl) + powl;
    double rd = 0.0D;
    double rd2 = 0.0D;
    
    if (l == 11) {
      
      j1 = 1.0D;
      rd = rand;
    }
    else if (l == 10) {
      
      j1 = -1.0D;
      rd = rand;
    }
    else if (l == 13) {
      
      i1 = 1.0D;
      rd2 = rand;
    }
    else {
      
      i1 = -1.0D;
      rd2 = rand;
    } 
    
    TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
    if (tileentitydispenser != null)
    {
      for (int r = 0; r < tileentitydispenser.getSizeInventory(); r++) {
        
        ItemStack itemstack = tileentitydispenser.getStackInSlot(r);

        //Точка появления в мире Entity
        double d = i + i1 * 0.6D + 0.5D; //x
        double d1 = j + 0.5D; //y
        double d2 = k + j1 * 0.6D + 0.5D; //z
        
        if (itemstack == null) {
          
          world.playAuxSFX(1001, i, j, k, 0);
        } else {
          if (itemstack.getItem() == RegisterItems.tennis) {
            
            EntityTennis tennis = new EntityTennis(world, d, d1, d2);
            tennis.setHeading(i1 + rd, y, j1 + rd2, (float)(0.9D + power), 6.0F);
            world.spawnEntityInWorld((Entity)tennis); //Поместить Entity в мир
            world.playSoundAtEntity((Entity)tennis, "sports.tennisshoot", 1.0F, 0.8F); //Проиграть звук выплевывания из раздатчика
            tileentitydispenser.decrStackSize(r, 1); //Уменьшить количество предметов в слоте
            return;
          } 
          if (itemstack.getItem() == RegisterItems.soccer) {
            
            EntitySoccer soccer = new EntitySoccer(world, d, d1, d2);
            soccer.setHeading(i1 + rd, y, j1 + rd2, (float)(1.9D + power), 6.0F);
            world.spawnEntityInWorld((Entity)soccer);
            world.playSoundAtEntity((Entity)soccer, "sports.footballshoot", 1.0F, 0.8F);
            tileentitydispenser.decrStackSize(r, 1);
            return;
          } 
          if (itemstack.getItem() == RegisterItems.baseball) {
            
            EntityBaseball baseball = new EntityBaseball(world, d, d1, d2);
            baseball.setHeading(i1, y, j1, (float)(1.1D + power), 6.0F);
            world.spawnEntityInWorld((Entity)baseball);
            world.playSoundAtEntity((Entity)baseball, "sports.baseballshoot", 1.0F, 0.8F);
            tileentitydispenser.decrStackSize(r, 1);
            return;
          } 
        } 
      }  } 
  }*/

  //public void updateTick(World world, int x, int y, int z, Random random) {
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isRemote && worldIn.isBlockIndirectlyGettingPowered(pos) > 0)
    {
      //dispenseItem2(worldIn, pos, random);
    }
  }
}