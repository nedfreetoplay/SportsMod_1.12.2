package net.sports.register;

import net.minecraft.block.Block;
import net.sports.block.BlockLauncher;
import net.sports.block.BlockPanier;
import net.sports.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class RegisterBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static Block panier = (new BlockPanier(Settings.getID("BlockPanier"), 11)).setHardness(1.4F).setResistance(10.0F);
    public static Block launcher = (new BlockLauncher(Settings.getID("BlockLauncher"))).setHardness(1.4F).setResistance(10.0F);
}
