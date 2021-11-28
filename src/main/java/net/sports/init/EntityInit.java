package net.sports.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.sports.Main;
import net.sports.Reference;
import net.sports.entity.*;

public class EntityInit {
    public static void registerEntities()
    {
        registerEntity("ball", EntityBall.class, Reference.ENTITY_BALL, 50, 11437146, 000000);
        /*registerEntity("baseball", EntityBaseball.class, Reference.ENTITY_BASEBALL, 160, 11437146, 000000);
        registerEntity("basket", EntityBasket.class, Reference.ENTITY_BASKET, 160, 11437146, 000000);
        registerEntity("soccer", EntitySoccer.class, Reference.ENTITY_SOCCER, 160, 11437146, 000000);
        registerEntity("tennis", EntityTennis.class, Reference.ENTITY_TENNIS, 160, 11437146, 000000);*/
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }
}
