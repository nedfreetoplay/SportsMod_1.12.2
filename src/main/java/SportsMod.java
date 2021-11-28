package net.sports;

import api.player.server.ServerPlayerAPI;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.lang.reflect.Field;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sports.entity.EntityBaseball;
import net.sports.entity.EntityBasket;
import net.sports.entity.EntitySoccer;
import net.sports.entity.EntitySportsmanServer;
import net.sports.entity.EntityTennis;
import net.sports.item.ItemGlove;
import net.sports.network.PacketPipeline;
import net.sports.settings.Settings;
import org.apache.logging.log4j.Level;

import static net.sports.register.RegisterItems.new_glove;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SportsMod {

    @SidedProxy(clientSide = "net.sports.client.client_Sports", serverSide = "net.sports.common_Sports")
    public static common_Sports proxy;
    @Mod.Instance
    public static SportsMod instance;
    public static Settings Settings;

    public SportsMod() {
        Settings = new Settings();
        Settings.loadOptions();
    }
    public static float[][] gcolor = new float[9][3];
    public static final PacketPipeline packetPipeline = new PacketPipeline();
    public static CreativeTabs tabSports = new SportsTab("tabSports"); public static boolean init = true;
    /*public static Block panier;
    public static Block launcher;
    public static Item glove = new Item();
    public static Item[] new_glove = new Item[9];

    public static Item racket;
    public static Item bat;
    public static Item baseballGlove;
    public static Item soccer;
    public static Item basket;
    public static Item tennis;
    public static Item baseball;*/

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        for (int i = 0; i < 9; i++) {
            new_glove[i] = new ItemGlove("ItemGlove_" + i);
        }
        (new float[3])[0] = 255.0F; (new float[3])[1] = 255.0F; (new float[3])[2] = 255.0F; gcolor[0] = new float[3];
        (new float[3])[0] = 255.0F; (new float[3])[1] = 0.0F; (new float[3])[2] = 0.0F; gcolor[1] = new float[3];
        (new float[3])[0] = 0.0F; (new float[3])[1] = 255.0F; (new float[3])[2] = 0.0F; gcolor[2] = new float[3];
        (new float[3])[0] = 0.0F; (new float[3])[1] = 100.0F; (new float[3])[2] = 255.0F; gcolor[3] = new float[3];
        (new float[3])[0] = 255.0F; (new float[3])[1] = 150.0F; (new float[3])[2] = 0.0F; gcolor[4] = new float[3];
        (new float[3])[0] = 255.0F; (new float[3])[1] = 230.0F; (new float[3])[2] = 0.0F; gcolor[5] = new float[3];
        (new float[3])[0] = 0.0F; (new float[3])[1] = 230.0F; (new float[3])[2] = 255.0F; gcolor[6] = new float[3];
        (new float[3])[0] = 150.0F; (new float[3])[1] = 0.0F; (new float[3])[2] = 255.0F; gcolor[7] = new float[3];
        (new float[3])[0] = 25.0F; (new float[3])[1] = 25.0F; (new float[3])[2] = 25.0F; gcolor[8] = new float[3];
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        packetPipeline.initialise();

        ServerPlayerAPI.register("EntitySportsmanServer", EntitySportsmanServer.class);
        proxy.registerRenderInformation();
        FMLCommonHandler.instance().bus().register(this);
    }

    @EventHandler
    public void postInitialise(FMLPostInitializationEvent evt) {
        packetPipeline.postInitialise();
        /*try {
            Field shuffle = Class.forName("org.spigotmc.SpigotConfig").getField("playerShuffle");
            shuffle.setAccessible(true);
            shuffle.set(null, Integer.valueOf(20));
        } catch (Throwable e) {
            FMLCommonHandler.instance().getFMLLogger().log(Level.FATAL, "Cannot find Thermos Core");
            FMLCommonHandler.instance().handleExit(1313);
        }*/
    }

    @SubscribeEvent
    public void tickEvent(TickEvent event) {
        proxy.tick(event);
    }

    public static boolean checkGlove(Item item) {
        for (int i = 0; i < 9; ) {
            if (new_glove[i] != item) { i++; continue; }
            return true;
        }
        return false;
    }

    public static int getGlove(Item item) {
        for (int i = 0; i < 9; ) {
            if (new_glove[i] != item) { i++; continue; }
            return i;
        }
        return -1;
    }

    public static double limite(double target, double limit) {
        if (target < -limit) {
            target = -limit;
        }
        if (target > limit) {
            target = limit;
        }
        return target;
    }

    public static double OptionVersion() {
        return 1.0D;
    }

    public static double IDsVersion() {
        return 1.0D;
    }
}
