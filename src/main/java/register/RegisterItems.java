package net.sports.register;

import net.minecraft.item.Item;
import net.sports.Sports;
import net.sports.item.ItemBall;
import net.sports.item.ItemBaseballGlove;
import net.sports.item.ItemBat;
import net.sports.item.ItemRacket;
import net.sports.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class RegisterItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static Item glove = new Item();
    public static Item[] new_glove = new Item[9];

    public static Item racket = new ItemRacket("itemRacket");
    public static Item bat = new ItemBat("itemBat");
    public static Item baseballGlove = new ItemBaseballGlove("itemBaseballGlove");

    public static Item soccer = new ItemBall("itemFootball", 1, Sports.FOOTBALL);
    public static Item basket = new ItemBall("itemBasket", 1, Sports.BASKETBALL);
    public static Item tennis = new ItemBall("itemTennis", 16, Sports.TENNIS);
    public static Item baseball = new ItemBall("itemBaseball", 16, Sports.BASEBALL);

}
