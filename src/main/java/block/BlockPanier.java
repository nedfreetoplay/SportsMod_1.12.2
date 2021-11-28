package net.sports.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.SportsMod;
import net.sports.client.client_Sports;
import net.sports.register.RegisterBlocks;
import net.sports.register.RegisterItems;

public class BlockPanier extends Block
{
  public BlockPanier(String name) {
    super(Material.IRON);
    this.setCreativeTab(SportsMod.tabSports);
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
    RegisterBlocks.BLOCKS.add(this);
    RegisterItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
  }

  //collisionRayTrace
  public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 vec3d, Vec3 vec3d1) {
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
  }
  
  public boolean canCollideCheck(int i, boolean flag) {
    return true;
  }
  
  public boolean getIsBlockSolid(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return true;
  }
  
  public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return true;
  }
  
  public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
    int var8 = par1World.getBlockMetadata(par2, par3, par4);
    
    setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
    super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
    super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
    super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public int tickRate() {
    return 20;
  }
  
  public void powa(World world, int i, int j, int k) {
    world.setBlockMetadataWithNotify(i, j, k, 1, 1);
    world.notifyBlocksOfNeighborChange(i, j, k, this);
    world.notifyBlocksOfNeighborChange(i, j + 1, k, this);
    world.scheduleBlockUpdate(i, j, k, this, tickRate());
    
    if (world.getBlock(i, j - 6, k) instanceof BlockPanier) {
      
      world.setBlockMetadataWithNotify(i, j - 6, k, 1, 1);
      world.notifyBlocksOfNeighborChange(i, j - 6, k, this);
      world.notifyBlocksOfNeighborChange(i, j - 6 + 1, k, this);
      world.scheduleBlockUpdate(i, j - 6, k, this, tickRate());
    } 
  }
  
  public void updateTick(World world, int i, int j, int k, Random random) {
    if (world.isRemote) {
      return;
    }
    
    if (world.getBlockMetadata(i, j, k) == 0) {
      return;
    }

    world.setBlockMetadataWithNotify(i, j, k, 0, 0);
    world.notifyBlocksOfNeighborChange(i, j, k, this);
    world.notifyBlocksOfNeighborChange(i, j - 1, k, this);
  }

  public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return (iblockaccess.getBlockMetadata(i, j, k) > 0) ? 15 : 0;
  }
}