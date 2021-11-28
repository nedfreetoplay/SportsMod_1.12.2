package net.sports.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ModelBall
  extends ModelBase
{
  public float Ja;
  public float Jb;
  public ModelRenderer ball;
  
  public void rendu(float f5) {
    GL11.glPushMatrix();
    float u0 = 0.0F;
    float u1 = 1.0F;
    float v0 = 0.0F;
    float v1 = 1.0F;
    this.Ja = f5;
    this.Jb = f5;
    float max = f5;
    
    float vl = f5;
    float vh = f5;

    
    GL11.glBegin(7);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(f5, this.Ja, f5);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(f5, this.Ja, 0.0F);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(0.0F, this.Jb, 0.0F);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(0.0F, this.Jb, f5);

    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(f5, 0.0F, f5);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(f5, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(f5, this.Ja, 0.0F);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(f5, this.Ja, f5);

    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(0.0F, 0.0F, f5);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(0.0F, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(f5, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(f5, 0.0F, f5);

    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(0.0F, this.Jb, f5);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(0.0F, this.Jb, 0.0F);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(0.0F, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(0.0F, 0.0F, f5);

    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(f5, this.Ja, 0.0F);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(f5, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(0.0F, 0.0F, 0.0F);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(0.0F, this.Jb, 0.0F);

    
    GL11.glTexCoord2f(u0, v0);
    GL11.glVertex3f(f5, 0.0F, f5);
    GL11.glTexCoord2f(u0, v1);
    GL11.glVertex3f(f5, this.Ja, f5);
    GL11.glTexCoord2f(u1, v1);
    GL11.glVertex3f(0.0F, this.Jb, f5);
    GL11.glTexCoord2f(u1, v0);
    GL11.glVertex3f(0.0F, 0.0F, f5);
    
    GL11.glEnd();
    GL11.glPopMatrix();
  }
  
  public void drawBB(AxisAlignedBB aabb) {
    float down = -((float)(aabb.maxY - aabb.minY));
    float up = (float)(aabb.maxY - aabb.minY);
    float front = (float)(aabb.maxZ - aabb.minZ) / 2.0F;
    float back = -((float)(aabb.maxZ - aabb.minZ)) / 2.0F;
    float left = (float)(aabb.maxX - aabb.minX) / 2.0F;
    float right = -((float)(aabb.maxX - aabb.minX)) / 2.0F;
    
    GL11.glPolygonMode(1032, 6913);
    GL11.glBegin(7);
    
    GL11.glVertex3f(left, 0.0F, front);
    GL11.glVertex3f(left, 0.0F, back);
    GL11.glVertex3f(left, up, back);
    GL11.glVertex3f(left, up, front);
    
    GL11.glVertex3f(left, up, front);
    GL11.glVertex3f(left, up, back);
    GL11.glVertex3f(left, 0.0F, back);
    GL11.glVertex3f(left, 0.0F, front);

    
    GL11.glVertex3f(right, up, front);
    GL11.glVertex3f(right, up, back);
    GL11.glVertex3f(right, 0.0F, back);
    GL11.glVertex3f(right, 0.0F, front);
    
    GL11.glVertex3f(right, 0.0F, front);
    GL11.glVertex3f(right, 0.0F, back);
    GL11.glVertex3f(right, up, back);
    GL11.glVertex3f(right, up, front);

    
    GL11.glVertex3f(left, 0.0F, back);
    GL11.glVertex3f(left, up, back);
    GL11.glVertex3f(right, up, back);
    GL11.glVertex3f(right, 0.0F, back);
    
    GL11.glVertex3f(right, 0.0F, back);
    GL11.glVertex3f(right, up, back);
    GL11.glVertex3f(left, up, back);
    GL11.glVertex3f(left, 0.0F, back);

    
    GL11.glVertex3f(left, 0.0F, front);
    GL11.glVertex3f(left, up, front);
    GL11.glVertex3f(right, up, front);
    GL11.glVertex3f(right, 0.0F, front);
    
    GL11.glVertex3f(right, 0.0F, front);
    GL11.glVertex3f(right, up, front);
    GL11.glVertex3f(left, up, front);
    GL11.glVertex3f(left, 0.0F, front);
    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnd();
    GL11.glPolygonMode(1032, 6914);
  }
}