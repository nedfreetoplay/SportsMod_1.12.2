package net.sports.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import net.sports.SportsMod;

public class Settings
{
  public static Hashtable IDs;
  private static File idsFile;
  
  public Settings() {
    IDs = new Hashtable<Object, Object>();
    
    IDs.put("EntityBasket", Integer.valueOf(7));
    IDs.put("EntityFootball", Integer.valueOf(3));
    IDs.put("EntityTennis", Integer.valueOf(8));
    IDs.put("EntityBaseball", Integer.valueOf(4));
    IDs.put("ItemBasket", Integer.valueOf(920));
    IDs.put("ItemFootball", Integer.valueOf(910));
    IDs.put("ItemTennis", Integer.valueOf(930));
    IDs.put("ItemBaseball", Integer.valueOf(940));
    for (int i = 0; i < 9; i++) {
      IDs.put("ItemGlove_" + i, Integer.valueOf(915 + i));
    }
    IDs.put("ItemRacket", Integer.valueOf(931));
    IDs.put("ItemBat", Integer.valueOf(941));
    IDs.put("ItemBaseballGlove", Integer.valueOf(942));
    IDs.put("BlockPanier", Integer.valueOf(910));
    IDs.put("BlockLauncher", Integer.valueOf(911));


    
    try {
      File file = new File("./mods/");
      idsFile = new File(file, "mod_SportsIDs.properties");
      if (!idsFile.exists())
      {
        createIDsFile();
      }
    }
    catch (Exception exception) {
      
      System.out.println("Failed to save options");
      exception.printStackTrace();
    } 
  }

  
  public void createIDsFile() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(idsFile));
      Iterator<Integer> itValue = IDs.values().iterator();
      Iterator<String> itKey = IDs.keySet().iterator();
      printwriter.println("Version:" + SportsMod.IDsVersion());
      while (itValue.hasNext()) {
        
        Integer value = itValue.next();
        String key = itKey.next();
        printwriter.println(key + ":" + value);
      } 
      printwriter.close();
    }
    catch (Exception exception) {
      
      System.out.println("Failed to save options");
      exception.printStackTrace();
    } 
  }
  
  public static int getID(String string) {
    return ((Integer)IDs.get(string)).intValue();
  }



  
  public void loadOptions() {
    try {
      if (!idsFile.exists()) {
        return;
      }
      
      BufferedReader bufferedreader = new BufferedReader(new FileReader(idsFile));
      float version = 0.0F;
      for (String s = ""; (s = bufferedreader.readLine()) != null;) {

        
        try {
          String[] as = s.split(":");
          Iterator<Integer> itValue = IDs.values().iterator();
          Iterator<String> itKey = IDs.keySet().iterator();
          int i = 0;
          while (itValue.hasNext())
          {
            Integer value = itValue.next();
            String key = itKey.next();
            if (as[0].equals("Version")) version = parseFloat(as[1]); 
            if (version < SportsMod.IDsVersion()) {
              
              createIDsFile();
              return;
            } 
            if (as[0].equals(key))
            {
              IDs.put(key, Integer.valueOf(Integer.parseInt(as[1])));
            }
            i++;
          }
        
        } catch (Exception e) {
          
          System.out.println("Skipping bad option: " + s);
          e.printStackTrace();
        } 
      } 
      
      bufferedreader.close();
    }
    catch (Exception exception) {
      
      System.out.println("Failed to load options");
      exception.printStackTrace();
    } 
  }
  
  private float parseFloat(String s) {
    if (s.equals("true"))
    {
      return 1.0F;
    }
    if (s.equals("false"))
    {
      return 0.0F;
    }
    
    return Float.parseFloat(s);
  }
}