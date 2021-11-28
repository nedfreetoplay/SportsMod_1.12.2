package net.sports.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.sports.Sports;
import net.sports.SportsMod;

public class EntityTennis extends EntityBall
{
  public EntityTennis(World world) {
    super(world);
  }
  
  public EntityTennis(World world, double d, double d1, double d2) {
    super(world, d, d1, d2);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.sport = Sports.TENNIS;
    setSize(0.2F, 0.2F);
    float f1 = 0.2F;
    this.width = 0.2F;
    this.boundingBox.setBounds(this.posX - this.width / 2.0D, this.posY - this.yOffset + this.ySize, this.posZ - this.width / 2.0D, this.posX + this.width / 2.0D, this.posY - this.yOffset + this.ySize + f1, this.posZ + this.width / 2.0D);
    this.gravite = 0.03F;
    this.coefcolision = 0.09D;
    this.forceRestituee = 1.0F;
    this.item = SportsMod.tennis;
    this.type = 3;
  }
  
  public void onUpdate() {
    if (this.soSoon < 10 && this.inhand && this.eplayer != null) {
      
      if (this.eplayer.rotationPitch > -40.0F && this.eplayer.rotationPitch < 10.0F)
        this.rotationPitch = this.eplayer.rotationPitch; 
      this.rotationYaw = this.eplayer.rotationYaw;
      EntityPlayer entityPlayer = this.eplayer;
      ((EntityLivingBase)entityPlayer).renderYawOffset = this.rotationYaw;
      
      this.posX = this.eplayer.posX + (MathHelper.cos((this.rotationYaw - 110.0F) / 180.0F * 3.1415927F) * -0.5F);
      this.posY = this.eplayer.posY + this.eplayer.getEyeHeight() - 1.4D + MathHelper.cos((this.rotationPitch + 40.0F) / 180.0F * 3.1415927F);
      this.posZ = this.eplayer.posZ + (MathHelper.sin((this.rotationYaw - 110.0F) / 180.0F * 3.1415927F) * -0.5F);
      setPosition(this.posX, this.posY, this.posZ);
    } else {
      super.onUpdate();
    } 
  }
  public void IAplay(Entity entity) {
    this.rotationPitch = entity.rotationPitch + 45.0F;
    this.rotationYaw = entity.rotationYaw;
    int lower = 5;
    int higher = 20;
    tir((((int)(Math.random() * (higher - lower)) + lower) * 2));
  }
  
  public void onCollideWithPlayer() {
    if (this.sportsmanServer.Cross > 0.0F) getInHand(); 
    if (this.sportsmanServer.Circle > 0.0F) tir2(SportsMod.limite(this.sportsmanServer.Circle, 20.0D)); 
  }
  
  public void tir(double puissanceTir) {
    float f = (float)puissanceTir / 10.0F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f);
    setHeading(this.motionX, this.motionY, this.motionZ, f, 1.0F);
    
    release();
  }
  
  public void tir2(double puissanceTir) {
    float f = (float)puissanceTir / 40.0F;
    this.motionX = 0.0D;
    this.motionZ = 0.0D;
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f);
    setHeading(this.motionX, this.motionY, this.motionZ, f, 1.0F);
    
    release();
  }
  
  public boolean attackEntityFrom(DamageSource damagesource, int i) {
    if (this.worldObj.isRemote || this.isDead)
    {
      return true;
    }
    this.soSoon = 15;
    this.inhand = false;
    this.motionY = -0.25D;
    return true;
  }
}