package net.sports.utils.handlers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.sports.entity.*;

public class RenderHandler {

    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBall.class, new IRenderFactory<EntityBall>() {
            @Override
            public Render<? super EntityBall> createRenderFor(RenderManager manager) {
                /*return new RenderBall(manager);*/
                return null;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBaseball.class, new IRenderFactory<EntityBaseball>() {
            @Override
            public Render<? super EntityBaseball> createRenderFor(RenderManager manager) {
                /*return new RenderBall(manager);*/
                return null;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBasket.class, new IRenderFactory<EntityBasket>() {
            @Override
            public Render<? super EntityBasket> createRenderFor(RenderManager manager) {
                /*return new RenderBall(manager);*/
                return null;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySoccer.class, new IRenderFactory<EntitySoccer>() {
            @Override
            public Render<? super EntitySoccer> createRenderFor(RenderManager manager) {
                return null;
            }
        });
        //RenderingRegistry.registerEntityRenderingHandler(EntitySportsmanServer.class, new IRenderFactory<EntitySportsmanServer>());
        RenderingRegistry.registerEntityRenderingHandler(EntityTennis.class, new IRenderFactory<EntityTennis>() {
            @Override
            public Render<? super EntityTennis> createRenderFor(RenderManager manager) {
                return null;
            }
        });
    }
}