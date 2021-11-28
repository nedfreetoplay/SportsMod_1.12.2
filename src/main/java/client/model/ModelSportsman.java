package net.sports.client.model;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.sports.Anim;
import net.sports.Sports;
import net.sports.SportsMod;
import net.sports.client.entity.PlayerParameter;
import org.lwjgl.opengl.GL11;

public class ModelSportsman extends ModelPlayerBase
{
  public ModelRenderer gloveLeft;
  float rot;
  
  public ModelSportsman(ModelPlayerAPI modelplayerapi) {
    super(modelplayerapi);

    this.rot = 0.0F;
    this.gloveLeft = new ModelRenderer((ModelBase)this.modelPlayer, 0, 0);
    this.gloveLeft.addBox(-2.0F, 5.6F, -3.0F, 6, 6, 6, 0.0F);
    this.gloveLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity var7) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, var7);
  }
  
  public void render(Entity entityliving, float par2, float par3, float par4, float par5, float par6, float par7) {
    setRotationAngles(par2, par3, par4, par5, par6, par7, entityliving);
    PlayerParameter playerParameter = PlayerParameter.getParameter(entityliving, "EntitySportsman");
    EntityPlayer player = (EntityPlayer)entityliving;
    ItemStack stack = player.inventory.getCurrentItem();
    playerParameter.timer.updateTimer();
    if (playerParameter.timer.elapsedTicks != 0)
      playerParameter.time++; 
    playerParameter.timerAnim.updateTimer();
    if (playerParameter.timerAnim.elapsedTicks != 0)
      playerParameter.animTime++; 
    if (playerParameter.sport == Sports.BASKETBALL)
      if (playerParameter.anim == Anim.SPINNING) {
        this.modelPlayer.bipedRightArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
        if (playerParameter.time > 16) {
          playerParameter.time = 0;
          playerParameter.reset();
        } 
      } else if (playerParameter.inHand) {
        this.modelPlayer.bipedLeftArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
        this.modelPlayer.bipedRightArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
      }  
    if (playerParameter.sport == Sports.FOOTBALL) {
      if (playerParameter.anim == Anim.ARRET && !playerParameter.inHand) {
        this.modelPlayer.bipedLeftArm.rotateAngleZ = -1.3F + entityliving.rotationPitch / 50.0F;
        this.modelPlayer.bipedRightArm.rotateAngleZ = 1.3F - entityliving.rotationPitch / 50.0F;
        this.modelPlayer.bipedRightArm.rotateAngleY = 0.0F;
        this.modelPlayer.bipedRightArm.rotateAngleX = 0.0F;
        if (playerParameter.time > 8) {
          playerParameter.time = 0;
          playerParameter.reset();
        } 
      } 
      if (playerParameter.inHand) {
        this.modelPlayer.bipedLeftArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
        this.modelPlayer.bipedRightArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
      } 
      if (playerParameter.anim == Anim.ARMER_TIR) {
        this.modelPlayer.bipedRightLeg.rotateAngleX = 0.5F;
      } else if (playerParameter.anim == Anim.TIR) {
        this.modelPlayer.bipedRightLeg.rotateAngleX = -0.5F;
        if (playerParameter.time > 2) {
          playerParameter.time = 0;
          playerParameter.reset();
        } 
      } 
      if (playerParameter.anim == Anim.ARMER_PASSE) {
        this.modelPlayer.bipedLeftLeg.rotateAngleX += 0.4F;
      } else if (playerParameter.anim == Anim.PASSE) {
        this.modelPlayer.bipedLeftLeg.rotateAngleX -= 0.4F;
        this.modelPlayer.bipedLeftLeg.rotateAngleY -= 0.5F;
        this.modelPlayer.bipedLeftLeg.rotateAngleZ = 0.1F;
        if (playerParameter.time > 2) {
          playerParameter.time = 0;
          playerParameter.reset();
          this.modelPlayer.bipedLeftLeg.rotateAngleZ = 0.0F;
        } 
      } 
    } 
    if (playerParameter.sport == Sports.TENNIS) {
      if (playerParameter.inHand) {
        float rpitch = entityliving.rotationPitch;
        if (rpitch < -40.0F)
          rpitch = -40.0F; 
        if (rpitch > 10.0F)
          rpitch = 10.0F; 
        this.modelPlayer.bipedLeftArm.rotateAngleX = -1.3F + rpitch / 50.0F;
      } 
      if (playerParameter.anim == Anim.ARMER_TIR) {
        this.modelPlayer.bipedRightArm.rotateAngleX = -1.0F;
        this.modelPlayer.bipedRightArm.rotateAngleY = 1.9F;
        playerParameter.rotXActuel = -0.1F - entityliving.rotationPitch / 50.0F;
      } else if (playerParameter.anim == Anim.TIR) {
        if (playerParameter.animTime < 10) {
          this.modelPlayer.bipedRightArm.rotateAngleX = -1.0F + playerParameter.animTime * 0.1F;
        } else {
          playerParameter.anim = Anim.INIT;
          this.modelPlayer.bipedRightArm.rotateAngleY = 0.0F;
          this.modelPlayer.bipedRightArm.rotateAngleX = 0.0F;
          playerParameter.animTime = 0;
        } 
      } 
    } 
    if (playerParameter.sport == Sports.BASEBALL) {
      if (playerParameter.anim == Anim.CATCH) {
        this.modelPlayer.bipedRightArm.rotateAngleX = -1.3F + entityliving.rotationPitch / 50.0F;
        this.modelPlayer.bipedRightArm.rotateAngleY = 0.3F;
        this.modelPlayer.bipedRightArm.rotateAngleZ = 0.0F;
        if (playerParameter.time > 8) {
          playerParameter.time = 0;
          playerParameter.reset();
        } 
      } 
      if (playerParameter.anim == Anim.ARMER_TIR) {
        if (playerParameter.animTime > 0) {
          this.rot += 0.59F;
          playerParameter.animTime = 0;
        } 
        this.modelPlayer.bipedRightArm.rotateAngleX += this.rot;
      } else {
        this.rot = 0.0F;
      } 
      if (playerParameter.entityPlayed != null) {
        playerParameter.entityPlayed.setRotationPoint(0.0F, 2.0F, 0.0F);
        playerParameter.entityPlayed.setRotate(this.modelPlayer.bipedRightArm.rotateAngleX, this.modelPlayer.bipedRightArm.rotateAngleY, this.modelPlayer.bipedRightArm.rotateAngleZ);
        playerParameter.setRotationPoint(0.0F, 2.0F, 0.0F);
        playerParameter.setRotate(this.modelPlayer.bipedRightArm.rotateAngleX, this.modelPlayer.bipedRightArm.rotateAngleY, this.modelPlayer.bipedRightArm.rotateAngleZ);
      } 
      if (player.isSneaking()) {
        if (playerParameter.anim == Anim.KNOCK) {
          if (playerParameter.animTime < 10) {
            this.modelPlayer.bipedRightArm.rotateAngleX = -2.0F + playerParameter.animTime * 0.3F;
          } else {
            playerParameter.anim = Anim.INIT;
            playerParameter.animTime = 0;
          } 
        } else {
          playerParameter.animTime = 0;
          this.modelPlayer.bipedRightArm.rotateAngleX = -2.0F;
          this.modelPlayer.bipedRightArm.rotateAngleY = 0.9F;
          playerParameter.rotXActuel = -0.1F - entityliving.rotationPitch / 50.0F;
        } 
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      } 
    } 
    if (stack != null)
      if (SportsMod.checkGlove(stack.getItem())) {
        this.gloveLeft.rotateAngleX = this.modelPlayer.bipedLeftArm.rotateAngleX;
        this.gloveLeft.rotateAngleY = this.modelPlayer.bipedLeftArm.rotateAngleY;
        this.gloveLeft.rotateAngleZ = this.modelPlayer.bipedLeftArm.rotateAngleZ;
        playerParameter.setRotationPoint(-4.0F, 2.0F, 0.0F);
        playerParameter.setRotate(this.modelPlayer.bipedLeftArm.rotateAngleX, this.modelPlayer.bipedLeftArm.rotateAngleY, this.modelPlayer.bipedLeftArm.rotateAngleZ);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(768, 32769);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        int i = SportsMod.getGlove(stack.getItem());
        GL11.glColor4f(SportsMod.gcolor[i][0] / 255.0F, SportsMod.gcolor[i][1] / 255.0F, SportsMod.gcolor[i][2] / 255.0F, 1.0F);
        this.gloveLeft.render(par7);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
      }  
    this.modelPlayer.bipedHead.render(par7);
    this.modelPlayer.bipedBody.render(par7);
    this.modelPlayer.bipedRightArm.render(par7);
    this.modelPlayer.bipedLeftArm.render(par7);
    this.modelPlayer.bipedRightLeg.render(par7);
    this.modelPlayer.bipedLeftLeg.render(par7);
    this.modelPlayer.bipedHeadwear.render(par7);
  }
}