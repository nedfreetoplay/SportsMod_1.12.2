package net.sports.utils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sports.init.BlocksInit;
import net.sports.init.EntityInit;
import net.sports.init.ItemsInit;
import net.sports.utils.interfaces.IHasModel;

@Mod.EventBusSubscriber
public class RegisterHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll(ItemsInit.ITEMS.toArray(new Item[0]));
    }

    //Попробую добавить регистрацию блоков
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll(BlocksInit.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll();
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
        for(Item item : ItemsInit.ITEMS){
            if(item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }
        for(Block block : BlocksInit.BLOCKS){
            if(block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }
    }

    public static void preInitRegistries()
    {
        EntityInit.registerEntities();
        RenderHandler.registerEntityRenders();
    }

    public static void initRegistries(){

    }

    public static void postInitRegistries(){

    }
}