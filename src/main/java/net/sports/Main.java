package net.sports;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.sports.proxy.ServerProxy;
import net.sports.utils.handlers.RegisterHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static ServerProxy proxy;

    @Mod.Instance
    public static Main instance;

    public static CreativeTabs tabSports = new SportsTab("tabSports");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        RegisterHandler.preInitRegistries();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RegisterHandler.initRegistries();
    }

    @Mod.EventHandler
    public void postInitialise(FMLPostInitializationEvent event) {
        RegisterHandler.postInitRegistries();
    }

    @SubscribeEvent
    public void tickEvent(TickEvent event){

    }
}
