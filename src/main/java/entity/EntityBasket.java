package net.sports.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.block.BlockPanier;
import net.sports.client.entity.PlayerParameter;

public class EntityBasket extends EntityBall
{
  boolean playingSound;
  
  public EntityBasket(World world) {
    super(world);
  }
  
  public EntityBasket(World world, double d, double d1, double d2) {
    super(world, d, d1, d2);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.sport = Sports.BASKETBALL;
    setSize(0.5F, 0.5F);
    this.yOffset = this.height / 2.0F - 0.2F;
    float f1 = 0.52F;
    this.width = 0.52F;
    this.playingSound = false;
    this.gravite = 0.05F;
    this.coefcolision = 0.09D;
    this.forceRestituee = 1.0F;
    this.item = SportsMod.basket;
    this.type = 1;
    this.boundingBox.setBounds(this.posX - this.width / 2.0D, this.posY - this.yOffset + this.ySize, this.posZ - this.width / 2.0D, this.posX + this.width / 2.0D, this.posY - this.yOffset + this.ySize + f1, this.posZ + this.width / 2.0D);
  }
  
  @SideOnly(Side.CLIENT)
  public void onUpdateClient() {
    if (this.soSoon < 10 && this.inhand && this.eplayer != null) {
      
      PlayerParameter playerParameter = PlayerParameter.getParameter((Entity)this.eplayer, "EntitySportsman");
      
      if (playerParameter.time < 40) {
        
        playerParameter.time++;
      }
      else {
        
        playerParameter.time = 0;
        playerParameter.anim = Anim.INIT;
      } 
      if (playerParameter.anim == Anim.SPINNING) {
        
        if (this.eplayer.rotationPitch > -30.0F && this.eplayer.rotationPitch < 30.0F) this.rotationPitch = this.eplayer.rotationPitch; 
        this.rotationYaw = this.eplayer.rotationYaw;
        EntityPlayer entityPlayer = this.eplayer;
        ((EntityLivingBase)entityPlayer).renderYawOffset = this.rotationYaw;
        
        this.posX = this.eplayer.posX + (MathHelper.cos((this.rotationYaw - 60.0F) / 180.0F * 3.1415927F) * -0.5F);
        this.posY = this.eplayer.posY + this.eplayer.getEyeHeight() - 1.4D + MathHelper.cos((this.rotationPitch + 40.0F) / 180.0F * 3.1415927F);
        this.posZ = this.eplayer.posZ + (MathHelper.sin((this.rotationYaw - 60.0F) / 180.0F * 3.1415927F) * -0.5F);
        setPosition(this.posX, this.posY, this.posZ);
        
        int lower = 5;
        int higher = 32;
        int r = ((int)(Math.random() * (higher - lower)) + lower) / 2;
        this.rotY += (16 + r);
        this.rotX = 0.0F;
        this.rotZ = 0.0F;
      }
      else {
        
        updateInHandPosition();
      } 
    } 
  }
  
  public void updateInHandPosition() {
    if (this.eplayer.rotationPitch > -30.0F && this.eplayer.rotationPitch < 30.0F) this.rotationPitch = this.eplayer.rotationPitch; 
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
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote) if (inHand()) { updateInHandPosition(); }
      else if ((this.motionX != 0.0D || this.motionY != 0.0D || this.motionZ != 0.0D) && !this.inhand)
      
      { int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getBlock(i, j, k) instanceof BlockPanier) {
          
          this.motionY *= 0.5D;
          if (this.motionY < 0.0D) {
            
            if (!this.worldObj.isRemote) {
              
              BlockPanier block = (BlockPanier)this.worldObj.getBlock(i, j, k);
              block.powa(this.worldObj, i, j, k);
            } 
            if (!this.playingSound) this.worldObj.playSoundAtEntity(this, "sports:panier", 1.0F, 1.0F); 
            this.playingSound = true;
          } 
        } else {
          this.playingSound = false;
        }  }
       
  }
  public void IAplay(Entity entity) {
    this.rotationPitch = entity.rotationPitch + 65.0F;
    this.rotationYaw = entity.rotationYaw;
    int lower = 5;
    int higher = 20;
    tir((((int)(Math.random() * (higher - lower)) + lower) / 2));
    this.motionY += 0.2D;
  }
  
  public void onCollideWithPlayer() {
    if (this.sportsmanServer.Cross > 0.0F) getInHand(); 
    if (this.sportsmanServer.Circle > 0.0F) tir(SportsMod.limite(this.sportsmanServer.Circle, 50.0D));
    
    this.rotationPitch = this.sportsmanServer.getRotationPitch();
    this.rotationYaw = this.sportsmanServer.getRotationYaw();
  }
  
  private void tir(double puissanceTir) {
    float f = (float)puissanceTir / 40.0F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f / 2.0F);
    setHeading(this.motionX, this.motionY + 0.4D, this.motionZ, f, 1.0F);
    
    release();
  }
  
  public boolean attackEntityFrom(DamageSource damagesource, int i) {
    release();
    this.motionY = -0.3D;

    return true;
  }
}