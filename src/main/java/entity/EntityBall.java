package net.sports.entity;

import api.player.server.IServerPlayer;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.Reference;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.entity.EntitySportsman;
import net.sports.client.settings.Settings;
import net.sports.network.AbstractPacket;
import net.sports.network.PacketInHand;
import net.sports.network.PacketSport;

public class EntityBall extends Entity
{
  protected static final int SABLE = 12;
  protected static final int PELOUSE = 2;
  protected static final int GRAVIER = 13;
  protected static final double REBOND_PELOUSE = 0.95D;
  protected static final double REBOND_SABLE = 0.8D;
  protected static final double REBOND_GRAVIER = 0.86D;
  protected static final double REBOND = 0.98D;
  protected static final double FREIN_PELOUSE = 0.96D;
  protected static final double FREIN_SABLE = 0.8D;
  protected static final double FREIN_GRAVIER = 0.92D;
  protected static final double FREIN = 0.98D;
  protected static final double FREIN_AIR = 0.95D;
  protected float forceRestituee;
  protected boolean colision;
  protected int sprint;
  protected int soSoon;
  public boolean inhand;
  protected float gravite;
  protected int vie;
  protected int sol;
  protected double playaX;
  protected double playaZ;
  protected float fx;
  protected float fy;
  protected float fz;
  protected int ballPosRotationIncrements;
  protected double ballX;
  protected double ballY;
  protected double ballZ;
  protected double ballYaw;
  protected double ballPitch;
  protected double velocityX;
  protected double velocityY;
  protected double velocityZ;
  protected int nextStepDistance;
  public float rotX;
  public float rotY;
  public float rotZ;
  public int couleur;
  private int i;
  private int j;
  private int k;
  protected double coefcolision;
  public EntitySportsmanServer sportsmanServer;
  public EntitySportsman sportsman;
  public EntityPlayerMP player;
  public EntityPlayer eplayer;
  protected Item item;
  public Sports sport;
  int type;
  public float rotateAngleX;
  public float rotateAngleY;
  public float rotateAngleZ;
  public float rotationPointX;
  public float rotationPointY;
  public float rotationPointZ;
  float distanceWalkedOnStepModified;
  private int cooldown;
  
  public EntityBall(World world) {
    super(world);
    this.preventEntitySpawning = true;
  }
  
  public EntityBall(World world, double x, double y, double z) {
    super(world);
    setPosition(x, y + this.yOffset, z);
    List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.5D, 0.5D, 0.5D));
    if (list != null && list.size() > 0) {
      int i1 = 0;
      if (0 < list.size()) {
        dropItem(this.item, 1);
        setDead();
        return;
      } 
    } 
    this.motionX = 0.0D;
    this.motionY = -0.06D;
    this.motionZ = 0.0D;
    this.prevPosX = x;
    this.prevPosY = y;
    this.prevPosZ = z;
    this.preventEntitySpawning = true;
  }
  
  protected void entityInit() {
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(1));
    this.dataWatcher.addObject(19, new Integer(0));
  }
  
  public void onCollideWithPlayer(EntityPlayer entityplayer) {
    if (!this.inhand) {
      this.eplayer = entityplayer;
      if (this.world.isRemote) {
        return;
      }
      if (this.player != entityplayer) {
        this.player = (EntityPlayerMP)entityplayer;
      }
      this.sportsmanServer = (EntitySportsmanServer)((IServerPlayer)this.player).getServerPlayerBase("EntitySportsmanServer");
      if (this.sportsmanServer == null) {
        return;
      }
      if (this.sportsmanServer.sport != this.type) {
        this.sportsmanServer.sport = this.type;
        SportsMod.packetPipeline.sendToAll((AbstractPacket)new PacketSport(this.player.hashCode(), this.type));
      } 
      if (this.sportsmanServer.Square && this.player.getCurrentEquippedItem() != null && this.player.getCurrentEquippedItem().getItem() == Items.DIAMOND) {
        dropItem(this.item, 1);
        setDead();
      } 
    } 
    if (this.sportsmanServer != null && this.cooldown == 0) {
      onCollideWithPlayer();
    }
  }

  
  public void onCollideWithPlayer() {}
  
  public void getInHand() {
    if (this.sportsmanServer.play != 0 || this.inhand) {
      return;
    }
    this.inhand = true;
    this.sportsmanServer.play = hashCode();
    SportsMod.packetPipeline.sendToAll((AbstractPacket)new PacketInHand(hashCode(), this.sportsmanServer.getPlayer().hashCode(), this.inhand));
    releaseKeys();
  }
  
  protected void release() {
    this.soSoon = 20;
    this.inhand = false;
    this.vie--;
    if (this.sportsmanServer == null) {
      return;
    }
    this.sportsmanServer.play = 0;
    releaseKeys();
    SportsMod.packetPipeline.sendToAll((AbstractPacket)new PacketInHand(hashCode(), this.sportsmanServer.getPlayer().hashCode(), this.inhand));
  }
  
  protected void releaseKeys() {
    this.sportsmanServer.Cross = 0.0F;
    this.sportsmanServer.Circle = 0.0F;
  }

  
  public void updateInHandPosition() {}
  
  public boolean inHand() {
    return (this.soSoon < 10 && this.inhand && this.eplayer != null);
  }
  
  public void onUpdate() {
    if (this.soSoon > 0) {
      this.soSoon--;
    }
    
    if (this.cooldown > 0) {
      this.cooldown--;
    }
    
    SportsMod.proxy.onUpdate(this);
  }
  
  public void onUpdateClient() {
    updatePhysic();
    this.rotX += (float)(((float)this.motionX * 180.0F) / 3.14159D);
    this.rotX %= 360.0F;
    this.rotZ += (float)(((float)this.motionZ * 180.0F) / 3.14159D);
    this.rotZ %= 360.0F;
  }

  public enum EnvironmentBlock{

  }
  
  public void onUpdateServer() {
    if (inHand()) {
      if (this.world.getEntityByID(getEntityId()) == null) {
        release();
        System.out.println("It's working!");
      } 
      if (this.sport == Sports.FOOTBALL) {
        if (this.eplayer.rotationPitch > -30.0F && this.eplayer.rotationPitch < 30.0F) {
          this.rotationPitch = this.eplayer.rotationPitch;
        }
        this.rotationYaw = this.eplayer.rotationYaw;
        EntityPlayer entityPlayer = this.eplayer;
        ((EntityLivingBase)entityPlayer).renderYawOffset = this.rotationYaw;
        this.posX = this.eplayer.posX + (MathHelper.cos((this.rotationYaw - 90.0F) / 180.0F * 3.1415927F) * -0.5F);
        this.posY = this.eplayer.posY + this.eplayer.getEyeHeight() - 1.6D + MathHelper.cos((this.rotationPitch + 40.0F) / 180.0F * 3.1415927F);
        this.posZ = this.eplayer.posZ + (MathHelper.sin((this.rotationYaw - 90.0F) / 180.0F * 3.1415927F) * -0.5F);
        setPosition(this.posX, this.posY, this.posZ);
        this.rotY = this.eplayer.rotationYaw - 70.0F;
        this.rotX = 0.0F;
        this.rotZ = 0.0F;
      } 
    } else {
      
      if (this.world.isRemote) {
        updatePhysic();
        this.rotX += (float)(((float)this.motionX * 2.0F * 180.0F) / 3.14159D);
        this.rotX %= 360.0F;
        this.rotZ += (float)(((float)this.motionZ * 2.0F * 180.0F) / 3.14159D);
        this.rotZ %= 360.0F;
        return;
      } 
      int i = MathHelper.floor(this.posX);
      int j = MathHelper.floor(this.posY);
      int k = MathHelper.floor(this.posZ);
      if (this.world.getBlockState(new BlockPos(i, j, k)).getBlock() == Blocks.WATER) {
        this.motionY += this.gravite + 0.02D;
      }
      this.sol = Block.getIdFromBlock(this.world.getBlockState(new BlockPos(i, j - 1, k)).getBlock());

      /*
      Поправки на блок через которую пройдет мяч
      P.S. Лучше изменить к след. версии, там вроде отменили id
       */
      int environmentBlock = Block.getIdFromBlock(this.world.getBlockState(new BlockPos(i, j, k)).getBlock());
      switch (environmentBlock) {
        case 31: //Blocks.DEADBUSH
          this.motionX *= 0.8D;
          this.motionY *= 0.8D;
          this.motionZ *= 0.8D;
          break;
        
        case 78: //Blocks.SNOW_LAYER
          this.motionX *= 0.9D;
          this.motionY *= 0.9D;
          this.motionZ *= 0.9D;
          break;
      } 
      
      this.sol = Block.getIdFromBlock(this.world.getBlockState(new BlockPos(i, j - 1, k)).getBlock());
      this.motionX = SportsMod.limite(this.motionX, 1.4D);
      this.motionZ = SportsMod.limite(this.motionZ, 1.4D);
      double lim = 0.005D;
      if (this.motionX < 0.005D && this.motionX > -0.005D) {
        this.motionX = 0.0D;
      }
      if (this.motionZ < 0.005D && this.motionZ > -0.005D) {
        this.motionZ = 0.0D;
      }
      if (this.motionY < 0.02D && this.motionY > -0.02D) {
        this.motionY = 0.0D;
      }
      moveEntity2(this.motionX, this.motionY, this.motionZ);
      this.motionY -= this.gravite;
      this.motionY *= 0.98D;
      this.motionX *= getFrein(this.sol);
      this.motionZ *= getFrein(this.sol);
      this.motionY *= getRebond(this.sol);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.2D, 0.0D, 0.2D));
      if (list != null && list.size() > 0) {
        for (int i2 = 0; i2 < list.size(); i2++) {
          Entity entity = list.get(i2);
          if (entity.canBePushed() && entity instanceof EntityBall) {
            entity.applyEntityCollision(this);
          }
        } 
      }
    } 
  }
  
  public void updatePhysic() {
    if (this.ballPosRotationIncrements > 0 && !this.inhand) {
      double d1 = this.posX + (this.ballX - this.posX) / this.ballPosRotationIncrements;
      double d2 = this.posY + (this.ballY - this.posY) / this.ballPosRotationIncrements;
      double d3 = this.posZ + (this.ballZ - this.posZ) / this.ballPosRotationIncrements;
      double d4;
      for (d4 = this.ballYaw - this.rotationYaw; d4 < -180.0D; d4 += 360.0D);
      while (d4 >= 180.0D) {
        d4 -= 360.0D;
      }
      this.rotationYaw += (float)(d4 / this.ballPosRotationIncrements);
      this.rotationPitch += (float)((this.ballPitch - this.rotationPitch) / this.ballPosRotationIncrements);
      this.ballPosRotationIncrements--;
      setPosition(d1, d2, d3);
      setRotation(this.rotationYaw, this.rotationPitch);
    } 
  }
  
  public void applyEntityCollision(Entity entity) {
    if (this.world.isRemote) {
      return;
    }
    double d = entity.posX - this.posX;
    double d2 = entity.posZ - this.posZ;
    double dy = entity.posY - this.posY;
    double d3 = d * d + d2 * d2 + dy * dy;
    d3 = MathHelper.sqrt(d3);
    d /= d3;
    d2 /= d3;
    dy /= d3;
    double d4 = 1.0D / d3;
    if (d4 > 1.0D) {
      d4 = 1.0D;
    }
    d *= d4;
    d2 *= d4;
    dy *= d4;
    d *= 0.1D;
    d2 *= 0.1D;
    dy *= 0.1D;
    double v = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    if (entity instanceof EntityLiving && v > 0.4D) {
      entity.attackEntityFrom(DamageSource.GENERIC, 2.0F);
      ((EntityLiving)entity).setAttackTarget((EntityLivingBase)this.player);
    }
    else if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
      IAplay(entity);
    } 
    if (entity instanceof EntityBall) {
      d *= (1.0F - this.entityCollisionReduction);
      d2 *= (1.0F - this.entityCollisionReduction);
      dy *= (1.0F - this.entityCollisionReduction);
      d *= 0.4D;
      d2 *= 0.4D;
      dy *= 0.4D;
      double d5 = entity.motionX + this.motionX;
      double d6 = entity.motionZ + this.motionZ;
      double d7 = entity.motionY + this.motionY;
      d5 /= 2.5D;
      d6 /= 2.5D;
      d7 /= 2.5D;
      this.motionX *= 0.2D;
      this.motionZ *= 0.2D;
      this.motionY *= 0.2D;
      addVelocity(d5 - d, d7 - dy, d6 - d2);
      entity.motionX *= 0.2D;
      entity.motionZ *= 0.2D;
      entity.motionY *= 0.2D;
      entity.addVelocity(d5 + d, d7 - dy, d6 + d2);
    }
    else if (!this.inhand && this.soSoon < 10) {
      d *= 0.949999988079071D;
      d2 *= 0.949999988079071D;
      dy *= 0.949999988079071D;
      d *= this.coefcolision;
      d2 *= this.coefcolision;
      dy *= this.coefcolision;
      addVelocity(-d * 1.1D, -dy, -d2 * 1.1D);
      entity.addVelocity(d / 4.0D, 0.0D, d2 / 4.0D);
      this.fx = -MathHelper.sin(entity.rotationYaw * 3.1415927F / 180.0F);
      this.fz = MathHelper.cos(entity.rotationYaw * 3.1415927F / 180.0F);
    } 
  }

  public void IAplay(Entity entity) {}
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double d) {
    double d2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    d2 *= 128.0D * Settings.ball;
    return (d < d2 * d2);
  }

  public AxisAlignedBB getBoundingBox() {
    return null;
  }
  
  public boolean canBePushed() {
    return (this.soSoon < 16 && !this.inhand);
  }
  
  public boolean canBeCollidedWith() {
    return (this.soSoon < 16 && !this.inhand);
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.1F;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
    this.ballX = d;
    this.ballY = d1;
    this.ballZ = d2;
    this.ballYaw = f;
    this.ballPitch = f1;
    this.ballPosRotationIncrements = 3;
    this.motionX = this.velocityX;
    this.motionY = this.velocityY;
    this.motionZ = this.velocityZ;
  }
  
  public void setVelocity(double d, double d1, double d2) {
    this.motionX = d;
    this.velocityX = d;
    this.motionY = d1;
    this.velocityY = d1;
    this.motionZ = d2;
    this.velocityZ = d2;
  }
  
  float abs(float n) {
    if (n > 0.0F) {
      return n;
    }
    return -n;
  }
  
  public void moveEntity2(double par1, double par3, double par5) {
    if (this.noClip) {
      this.getEntityBoundingBox().offset(par1, par3, par5);
      this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
      this.posY = this.getEntityBoundingBox().minY + this.yOffset - this.ySize;
      this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
    } else {
      
      this.world.profiler.startSection("move");
      this.ySize *= 0.4F;
      double d3 = this.posX;
      double d4 = this.posY;
      double d5 = this.posZ;
      if (this.isInWeb) {
        this.isInWeb = false;
        par1 *= 0.25D;
        par3 *= 0.05000000074505806D;
        par5 *= 0.25D;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      double d6 = par1;
      double d7 = par3;
      double d8 = par5;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().copy();
      boolean flag = false;

      List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(par1, par3, par5));
      for (int i = 0; i < list.size(); i++) {
        par3 = ((AxisAlignedBB)list.get(i)).calculateYOffset(this.getEntityBoundingBox(), par3);
      }
      this.getEntityBoundingBox().offset(0.0D, par3, 0.0D);
      if (!this.field_70135_K && d7 != par3) {
        par5 = 0.0D;
        par3 = 0.0D;
        par1 = 0.0D;
      } 
      boolean flag2 = (this.onGround || (d7 != par3 && d7 < 0.0D));
      for (int j = 0; j < list.size(); j++) {
        par1 = ((AxisAlignedBB)list.get(j)).calculateXOffset(this.getEntityBoundingBox(), par1);
      }
      this.getEntityBoundingBox().offset(par1, 0.0D, 0.0D);
      if (!this.field_70135_K && d6 != par1) {
        par5 = 0.0D;
        par3 = 0.0D;
        par1 = 0.0D;
      } 
      this.j = 0;
      while (this.j < list.size()) {
        par5 = ((AxisAlignedBB)list.get(this.j)).calculateZOffset(this.getEntityBoundingBox(), par5);
        this.j++;
      } 
      this.getEntityBoundingBox().offset(0.0D, 0.0D, par5);
      if (!this.field_70135_K && d8 != par5) {
        par5 = 0.0D;
        par3 = 0.0D;
        par1 = 0.0D;
      } 
      if (this.stepHeight > 0.0F && flag2 && this.ySize < 0.05F && (d6 != par1 || d8 != par5)) {
        double d1 = par1;
        double d2 = par3;
        double d9 = par5;
        par1 = d6;
        par3 = this.stepHeight;
        par5 = d8;
        AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox().copy();
        this.getEntityBoundingBox().setBB(axisalignedbb);
        list = this.world.getEntityBoundingBox()(this, this.getEntityBoundingBox().addCoord(d6, par3, d8));
        for (int k = 0; k < list.size(); k++) {
          par3 = ((AxisAlignedBB)list.get(k)).calculateYOffset(this.getEntityBoundingBox(), par3);
        }
        this.getEntityBoundingBox().offset(0.0D, par3, 0.0D);
        if (!this.field_70135_K && d7 != par3) {
          par5 = 0.0D;
          par3 = 0.0D;
          par1 = 0.0D;
        } 
        this.k = 0;
        while (this.k < list.size()) {
          par1 = ((AxisAlignedBB)list.get(this.k)).calculateXOffset(this.getEntityBoundingBox(), par1);
          this.k++;
        } 
        this.getEntityBoundingBox().offset(par1, 0.0D, 0.0D);
        if (!this.field_70135_K && d6 != par1) {
          par5 = 0.0D;
          par3 = 0.0D;
          par1 = 0.0D;
        } 
        this.k = 0;
        while (this.k < list.size()) {
          par5 = ((AxisAlignedBB)list.get(this.k)).calculateZOffset(this.getEntityBoundingBox(), par5);
          this.k++;
        } 
        this.getEntityBoundingBox().offset(0.0D, 0.0D, par5);
        if (!this.field_70135_K && d8 != par5) {
          par5 = 0.0D;
          par3 = 0.0D;
          par1 = 0.0D;
        } 
        if (!this.field_70135_K && d7 != par3) {
          par5 = 0.0D;
          par3 = 0.0D;
          par1 = 0.0D;
        } else {
          
          par3 = -this.stepHeight;
          this.k = 0;
          while (this.k < list.size()) {
            par3 = ((AxisAlignedBB)list.get(this.k)).calculateYOffset(this.getEntityBoundingBox(), par3);
            this.k++;
          }

          this.getEntityBoundingBox().offset(0.0D, par3, 0.0D);
        } 
        if (d1 * d1 + d9 * d9 >= par1 * par1 + par5 * par5) {
          par1 = d1;
          par3 = d2;
          par5 = d9;
          this.getEntityBoundingBox().setBB(axisalignedbb2);
        } 
      } 
      this.world.profiler.endSection();
      this.world.profiler.startSection("rest");
      this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
      this.posY = this.getEntityBoundingBox().minY + this.yOffset - this.ySize;
      this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
      this.isCollidedHorizontally = (d6 != par1 || d8 != par5);
      this.isCollidedVertically = (d7 != par3);
      this.onGround = (d7 != par3 && d7 < 0.0D);
      this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
      updateFallState(par3, this.onGround);
      if (d6 != par1) {
        rebondX();
      }
      if (d7 != par3) {
        rebondY();
      }
      if (d8 != par5) {
        rebondZ();
      }
      double d10 = this.posX - d3;
      double d11 = this.posY - d4;
      double d12 = this.posZ - d5;
      if (canTriggerWalking() && this.getRidingEntity() == null) {
        int j2 = MathHelper.floor(this.posX);
        int k = MathHelper.floor(this.posY - 0.20000000298023224D - this.yOffset);
        int l = MathHelper.floor(this.posZ);
        Block block = this.world.getBlockState(new BlockPos(j2, k, l)).getBlock();
        /*
          Доделать EnumRenderType
         */
        int i2 = this.world.getBlockState(new BlockPos(j2, k - 1, l)).getBlock().getRenderType();
        if (i2 == 11 || i2 == 32 || i2 == 21) {
          block = this.world.getBlockState(new BlockPos(j2, k - 1, l)).getBlock();
        }
        if (block != Blocks.LADDER) {
          d11 = 0.0D;
        }
        this.distanceWalkedModified += (float)(MathHelper.sqrt(d10 * d10 + d12 * d12) * 0.6D);
        this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt(d10 * d10 + d11 * d11 + d12 * d12) * 0.6D);
        if (this.distanceWalkedOnStepModified > this.nextStepDistance && j2 > 0) {
          this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
          if (isInWater()) {
            float f = MathHelper.sqrt(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;
            if (f > 1.0F) {
              f = 1.0F;
            }
            playSound("liquid.swim", f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
          } 
          func_145780_a(j2, k, l, block);
          block.onEntityWalking(this.world, j2, k, l, this);
        } 
      } 
      try {
        func_145775_I();
      }
      catch (Throwable throwable) {
        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
        CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
        addEntityCrashInfo(crashreportcategory);
        throw new ReportedException(crashreport);
      } 
      boolean flag3 = isWet();
      this.world.profiler.endSection();
    } 
  }
  
  protected void rebondX() {
    this.motionX *= -0.96D;
    if (this.motionX > 0.05D || this.motionX < -0.05D) {
      if (this instanceof EntityTennis) {
        this.world.playSoundAtEntity(this, "sports:tennisrebond", 0.7F, 0.88F + abs((float)this.motionX));
      }
      else if (this instanceof EntitySoccer) {
        this.world.playSoundAtEntity(this, "sports:footballrebond", 0.7F, 0.88F + abs((float)this.motionX));
      } 
      if (this instanceof EntityBasket && !(this.world.getBlock(this.i, this.j, this.k) instanceof net.sports.block.BlockPanier)) {
        this.world.playSoundAtEntity(this, "sports:basketballrebond", 0.2F, 0.98F + abs((float)this.motionX));
      }
    } 
  }
  
  protected void rebondY() {
    this.motionY *= -getRebond(this.sol);
    if (this.sol == 4 && (this.motionY > 0.15D || this.motionY < -0.15D)) {
      int lower = 10;
      int higher = 16;
      double rx = ((int)(Math.random() * 6.0D) + 10 - 12);
      double rz = ((int)(Math.random() * 6.0D) + 10 - 12);
      this.motionX += rx / 50.0D;
      this.motionZ += rz / 50.0D;
    } 
    if (this.motionY > 0.05D) {
      if (this instanceof EntityTennis) {
        //this.world.playSoundAtEntity(this, "sports:tennisrebond", 0.4F + abs((float)this.motionY), 1.2F);
        this.world.playSound(this, this.posX, this.posY, this.posZ, new SoundEvent(new ResourceLocation(Reference.MODID + ":tennisrebond")), SoundCategory.NEUTRAL, 0.4F + abs((float)this.motionY), 1.2F);
      }
      else if (this instanceof EntitySoccer) {
        this.world.playSoundAtEntity(this, "sports:footballrebond", 0.4F, 1.3F);
      } 
      if (this instanceof EntityBasket && !(this.world.getBlock(this.i, this.j, this.k) instanceof net.sports.block.BlockPanier)) {
        this.world.playSoundAtEntity(this, "sports:basketballrebond", 0.2F, 1.0F);
      }
    } 
  }
  
  protected void rebondZ() {
    this.motionZ *= -0.96D;
    if (this.motionZ > 0.05D || this.motionZ < -0.05D) {
      if (this instanceof EntityTennis) {
        this.world.playSoundAtEntity(this, "sports:tennisrebond", 0.7F, 0.88F + abs((float)this.motionZ));
      }
      else if (this instanceof EntitySoccer) {
        this.world.playSoundAtEntity(this, "sports:footballrebond", 0.7F, 0.88F + abs((float)this.motionZ));
      } 
      if (this instanceof EntityBasket && !(this.world.getBlockState(new BlockPos(this.i, this.j, this.k)) instanceof net.sports.block.BlockPanier)) {
        this.world.playSoundAtEntity(this, "sports:basketballrebond", 0.2F, 0.98F + abs((float)this.motionZ));
      }
    } 
  }

  /*
  Не очень понял что это, но походу установка курса вращения?
   */
  public void setHeading(double x, double y, double z, float f, float f1) {
    //Длина вектора
    float f2 = MathHelper.sqrt(x * x + y * y + z * z);
    //Делим каждую ось на длину вектора
    x /= f2;
    y /= f2;
    z /= f2;
    //Прибавляем
    x += this.rand.nextGaussian() * 0.0075D * f1;
    y += this.rand.nextGaussian() * 0.0075D * f1;
    z += this.rand.nextGaussian() * 0.0075D * f1;
    x *= f;
    y *= f;
    z *= f;
    this.motionX = x;
    this.motionY = y + 0.2D;
    this.motionZ = z;
    float f3 = MathHelper.sqrt(x * x + z * z);
    float n = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
    this.rotationYaw = n;
    this.prevRotationYaw = n;
    float n2 = (float)(Math.atan2(y, f3) * 180.0D / Math.PI);
    this.rotationPitch = n2;
    this.prevRotationPitch = n2;
  }
  
  protected boolean canFall(int Sol) {
    return (Sol == 0 || Sol == 37 || Sol == 38 || Sol == 39 || Sol == 40 || Sol == 50 || Sol == 55 || Sol == 59 || Sol == 66 || Sol == 70);
  }

  /*
  Получить отскок(Хрен знает как, но это надо переделать и избавиться от Sol(Это id block'а))
   */
  protected double getRebond(int Sol) {
    if (Sol == 2) {
      return 0.95D * this.forceRestituee;
    }
    if (Sol == 12) {
      return 0.8D * this.forceRestituee;
    }
    if (Sol == 13) {
      return 0.86D * this.forceRestituee;
    }
    return 0.98D * this.forceRestituee;
  }
  
  protected double getFrein(int Sol) {
    if (Sol == 2) {
      return 0.96D;
    }
    if (Sol == 12) {
      return 0.8D;
    }
    if (Sol == 13) {
      return 0.92D;
    }
    return 0.98D;
  }
  
  public void setRotate(float x, float y, float z) {
    this.rotateAngleX = x;
    this.rotateAngleY = y;
    this.rotateAngleZ = z;
  }
  
  public void setRotationPoint(float x, float y, float z) {
    this.rotationPointX = x;
    this.rotationPointY = y;
    this.rotationPointZ = z;
  }
  
  protected void readEntityFromNBT(NBTTagCompound var1) {}
  
  protected void writeEntityToNBT(NBTTagCompound var1) {}
}