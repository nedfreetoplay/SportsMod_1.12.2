package net.sports.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGloves extends ModelBase
{
  public ModelRenderer glove;
  float r = 0.0F;
  
  public ModelGloves() {
    this.glove = new ModelRenderer(this, 0, 8);
    this.glove.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
    this.glove.setRotationPoint(0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.glove.rotateAngleX = 0.1F;
    this.glove.rotateAngleY = 0.2F;
    this.glove.rotateAngleZ = 0.0F;
    this.glove.render(f5);
  }
}