package net.sports.client.settings;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.sports.common_Sports;
import net.sports.network.JumpsEvent;

public class KeyBindings
{
  public static KeyBinding key_Cross;
  public static KeyBinding key_Circle;
  public static KeyBinding key_Triangle;
  public static KeyBinding key_Square;
  
  public static void init() {
    key_Cross = new KeyBinding("Cross", 50, "Sports");
    key_Circle = new KeyBinding("Circle", 38, "Sports");
    key_Triangle = new KeyBinding("Triangle", 37, "Sports");
    key_Square = new KeyBinding("Square", 36, "Sports");
    common_Sports.register_event(new JumpsEvent());
    
    ClientRegistry.registerKeyBinding(key_Cross);
    ClientRegistry.registerKeyBinding(key_Circle);
    ClientRegistry.registerKeyBinding(key_Triangle);
    ClientRegistry.registerKeyBinding(key_Square);
  }
}