package net.sports.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.sports.Sports;
import net.sports.SportsMod;

public class EntitySoccer extends EntityBall
{
  public EntitySoccer(World world) {
    super(world);
  }
  
  public EntitySoccer(World world, double d, double d1, double d2) {
    super(world, d, d1, d2);
  }

  protected void entityInit() {
    super.entityInit();
    this.sport = Sports.FOOTBALL;
    setSize(0.42F, 0.42F);
    this.yOffset = this.height / 2.0F - 0.1F;
    float f1 = 0.42F;
    this.width = 0.42F;
    this.boundingBox.setBounds(this.posX - this.width / 2.0D, this.posY - this.yOffset + this.ySize, this.posZ - this.width / 2.0D, this.posX + this.width / 2.0D, this.posY - this.yOffset + this.ySize + 0.41999998688697815D, this.posZ + this.width / 2.0D);
    this.gravite = 0.05F;
    this.coefcolision = 0.8D;
    this.forceRestituee = 1.0F;
    this.item = SportsMod.soccer;
    this.type = 2;
  }
  
  private void passe(double puissancePasse) {
    float f = (float)puissancePasse / 40.0F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionY = 0.0D;
    setHeading(this.motionX, this.motionY, this.motionZ, f, 1.0F);
    this.worldObj.playSoundAtEntity(this, "sports:footballshoot", 0.4F, 1.0F);
    release();
  }
  
  private void tir(double puissanceTir) {
    float f = (float)puissanceTir / 20.0F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f / 2.0F);
    setHeading(this.motionX, this.motionY, this.motionZ, f, 1.0F);
    this.worldObj.playSoundAtEntity(this, "sports:footballshoot", 0.7F, 0.88F + f / 10.0F);
    release();
  }

  
  public void IAplay(Entity entity) {
    this.rotationPitch = entity.rotationPitch + 35.0F;
    this.rotationYaw = entity.rotationYaw;
    int lower = 5;
    int higher = 20;
    tir((((int)(Math.random() * 15.0D) + 5) / 4));
    this.motionY += 0.2D;
  }

  
  public void onCollideWithPlayer() {
    this.rotationPitch = (this.sportsmanServer.getPlayer()).rotationPitch - 40.0F;
    this.rotationYaw = (this.sportsmanServer.getPlayer()).rotationYaw;
    if (this.sportsmanServer.Cross > 0.0F) {
      passe(SportsMod.limite(this.sportsmanServer.Cross, 30.0D));
    } else if (this.sportsmanServer.Circle > 0.0F) {
      tir(SportsMod.limite(this.sportsmanServer.Circle, 50.0D));
    } 
  }
}