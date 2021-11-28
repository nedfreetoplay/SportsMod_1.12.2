package net.sports.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.sports.Reference;
import net.sports.entity.EntityBall;
import net.sports.entity.model.ModelBall;

import javax.annotation.Nullable;

public class RenderBall extends Render {

    public static ResourceLocation TEXTURE_2D;
    public static ResourceLocation CUBE;
    public static ResourceLocation SPHERE;
    private String name;
    private float scale2d;
    private float scaleCube;
    private float scaleSphere;
    protected ModelBall modelBall;


    public RenderBall(RenderManager manager, String name, float scale2d, float scaleCube, float scaleSphere) {
        super(manager);

        this.modelBall = new ModelBall();
        name = name;
        this.scale2d = scale2d;
        this.scaleCube = scaleCube;
        this.scaleSphere = scaleSphere;
        TEXTURE_2D = new ResourceLocation(Reference.MODID + ":textures/entity/" + name + ".png");
        CUBE = new ResourceLocation(Reference.MODID + ":textures/entity/" + name + ".png");
        SPHERE = new ResourceLocation(Reference.MODID + ":textures/entity/" + name + ".png");
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        EntityBall ball = (EntityBall)entity;
    }
}
