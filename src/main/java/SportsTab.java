package net.sports;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sports.register.RegisterItems;

public class SportsTab extends CreativeTabs
{
  public SportsTab(String tabLabel) {
    super(tabLabel);
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public ItemStack getTabIconItem() {
    return new ItemStack(RegisterItems.soccer);
  }
}