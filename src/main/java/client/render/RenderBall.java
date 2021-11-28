package net.sports.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.sports.client.model.ModelBall;
import net.sports.client.settings.Settings;
import net.sports.entity.EntityBall;
import org.lwjgl.opengl.GL11;

public class RenderBall extends Render
{
  ResourceLocation t2d;
  ResourceLocation cubic;
  ResourceLocation spheric;
  protected ModelBall modelBall;
  
  public RenderBall(String s, float sc, float sc3, float scS) {
    this.modelBall = new ModelBall();
    this.name = s;
    this.scale = sc;
    this.scale3 = sc3;
    this.scaleS = scS;
    
    this.t2d = new ResourceLocation("sports:textures/items/" + this.name + ".png");
    this.cubic = new ResourceLocation("sports:textures/items/cubic/" + this.name + ".jpg");
    this.spheric = new ResourceLocation("sports:textures/items/spheric/" + this.name + ".jpg");
  }

  private String name;
  
  private float scale;
  
  private float scale3;
  private float scaleS;
  
  public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
    EntityBall ball = (EntityBall)entity;
    ball.updatePhysic();
    GL11.glPushMatrix();
    GL11.glEnable(32826);
    
    if (Settings.precision == 0) {
      
      GL11.glTranslatef((float)d, (float)(d1 + (entity.height / 5.0F)), (float)d2);
      
      GL11.glScalef(this.scale, this.scale, this.scale);
      (Minecraft.getMinecraft()).renderEngine.bindTexture(this.t2d);
      Tessellator tessellator = Tessellator.instance;
      GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      float f6 = 1.0F;
      float f7 = 0.5F;
      float f8 = 0.25F;
      tessellator.addVertexWithUV((0.0F - f7), (0.0F - f8), 0.0D, 0.0D, 0.0D);
      tessellator.addVertexWithUV((f6 - f7), (0.0F - f8), 0.0D, 1.0D, 0.0D);
      tessellator.addVertexWithUV((f6 - f7), (1.0F - f8), 0.0D, 1.0D, 1.0D);
      tessellator.addVertexWithUV((0.0F - f7), (1.0F - f8), 0.0D, 0.0D, 1.0D);
      tessellator.draw();
    }
    else if (Settings.precision == 1) {
      
      GL11.glTranslatef((float)(d - (entity.width / 2.0F)), (float)d1, (float)(d2 - (entity.width / 2.0F)));
      
      (Minecraft.getMinecraft()).renderEngine.bindTexture(this.cubic);
      this.modelBall.rendu(this.scale3);
    }
    else {
      
      GL11.glTranslatef((float)d, (float)(d1 + (entity.height / 2.0F)), (float)d2);
      
      if (ball.inhand) child(ball, f);
      
      int n = 10;
      if (Settings.precision == 2) { n = 18; }
      else if (Settings.precision == 3) { n = 26; }
      else if (Settings.precision == 4) { n = 34; }
      else if (Settings.precision == 5) { n = 42; }
      else if (Settings.precision == 6) { n = 50; }
      else { Settings.precision = 0; }
       (Minecraft.getMinecraft()).renderEngine.bindTexture(this.spheric);
      EntityBall entity2 = (EntityBall)entity;
      GL11.glScalef(this.scaleS, this.scaleS, this.scaleS);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 0.0F);
      GL11.glRotatef(180.0F - entity2.rotX, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F - entity2.rotZ, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(180.0F - entity2.rotY, 0.0F, 1.0F, 0.0F);
      
      double r = 260.0D;

      Vec3 c = Vec3.createVectorHelper(d, d1, d2);
      Vec3 e = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
      Vec3 p = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
      
      float PI = 3.1415927F;
      for (int j = 0; j < n / 2; j++) {
        
        double theta1 = ((j * 2) * PI / n - PI / 2.0F);
        double theta2 = (((j + 1) * 2) * PI / n - PI / 2.0F);
        
        GL11.glBegin(8);
        
        for (int i = 0; i <= n; i++) {
          
          double theta3 = ((i * 2) * PI / n);
          
          e.xCoord = Math.cos(theta2) * Math.cos(theta3);
          e.yCoord = Math.sin(theta2);
          e.zCoord = Math.cos(theta2) * Math.sin(theta3);
          c.xCoord += r * e.xCoord;
          c.yCoord += r * e.yCoord;
          c.zCoord += r * e.zCoord;
          
          GL11.glNormal3f((float)e.xCoord, (float)e.yCoord, (float)e.zCoord);
          GL11.glTexCoord2f((i / n), (2 * (j + 1) / n));
          GL11.glVertex3f((float)p.xCoord, (float)p.yCoord, (float)p.zCoord);
          
          e.xCoord = Math.cos(theta1) * Math.cos(theta3);
          e.yCoord = Math.sin(theta1);
          e.zCoord = Math.cos(theta1) * Math.sin(theta3);
          c.xCoord += r * e.xCoord;
          c.yCoord += r * e.yCoord;
          c.zCoord += r * e.zCoord;
          
          GL11.glNormal3f((float)e.xCoord, (float)e.yCoord, (float)e.zCoord);
          GL11.glTexCoord2f((i / n), (2 * j / n));
          GL11.glVertex3f((float)p.xCoord, (float)p.yCoord, (float)p.zCoord);
        } 
        GL11.glEnd();
      } 
    } 
    GL11.glDisable(32826);
    GL11.glPopMatrix();
  }
  
  public void child(EntityBall ball, float f) {
    GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
    
    float par1 = 0.29F;
    GL11.glTranslatef(ball.rotationPointX * par1, ball.rotationPointY * par1, ball.rotationPointZ * par1);
    
    if (ball.rotateAngleZ != 0.0F)
    {
      GL11.glRotatef(ball.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
    }
    
    if (ball.rotateAngleY != 0.0F)
    {
      GL11.glRotatef(ball.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
    }
    
    if (ball.rotateAngleX != 0.0F)
    {
      GL11.glRotatef(-ball.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
    }
    GL11.glTranslatef(-ball.rotationPointX * par1, -ball.rotationPointY * par1, -ball.rotationPointZ * par1);
  }

  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return this.t2d;
  }
}