package net.sports.utils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sports.register.RegisterBlocks;
import net.sports.register.RegisterEntity;
import net.sports.register.RegisterItems;
import net.sports.utils.interfaces.IHasModel;

@Mod.EventBusSubscriber
class RegisterHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll(RegisterItems.ITEMS.toArray(new Item[0]));
    }

    //Попробую добавить регистрацию блоков
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll(RegisterBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register event){
        event.getRegistry().registerAll();
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
        for(Item item : RegisterItems.ITEMS){
            if(item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }
        for(Block block : RegisterBlocks.BLOCKS){
            if(block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }
    }

    public static void preInitRegistries()
    {
        RegisterEntity.registerEntities();
        RenderHandler.registerEntityRenders();
    }
}