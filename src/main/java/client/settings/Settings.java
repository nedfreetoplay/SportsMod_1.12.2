package net.sports.client.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.sports.SportsMod;
import net.sports.client.gui.GuiField;

public class Settings
{
  public static Hashtable IDs;
  public static GuiField[] textFields;
  
  public Settings(Minecraft minecraft) {
    this.guiScale = 0;
    this.mc = minecraft;
    
    IDs = new Hashtable<Object, Object>();
    
    IDs.put("EntityBasket", Integer.valueOf(7));
    IDs.put("EntityFootball", Integer.valueOf(3));
    IDs.put("EntityTennis", Integer.valueOf(8));
    IDs.put("EntityBaseball", Integer.valueOf(4));
    IDs.put("ItemBasket", Integer.valueOf(920));
    IDs.put("ItemFootball", Integer.valueOf(910));
    IDs.put("ItemTennis", Integer.valueOf(930));
    IDs.put("ItemBaseball", Integer.valueOf(940));
    IDs.put("ItemGlove", Integer.valueOf(915));
    IDs.put("ItemRacket", Integer.valueOf(931));
    IDs.put("ItemBat", Integer.valueOf(941));
    IDs.put("ItemBaseballGlove", Integer.valueOf(942));

    textFields = new GuiField[IDs.size()];

    try {
      File file = new File("./mods/");
      optionsFile = new File(file, "mod_Sports.properties");
      if (!optionsFile.exists()) {
        
        PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
        printwriter.println("Version:" + SportsMod.OptionVersion());
        printwriter.println("BallsRenderDistance:0.5");
        printwriter.println("BallsRenderPrecision:0");
        printwriter.println("HUD:0");
        printwriter.println("KeyCrossSensitivity:0.5");
        printwriter.println("KeyCircleSensitivity:0.5");
        printwriter.close();
      } 
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
    
    ball = 0.5F;
    precision = 0;
    cross = 0.5F;
    circle = 0.5F;
    hud = 0;
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
  
  public Settings() {
    this.guiScale = 0;
  }
  
  public static int getID(String string) {
    return ((Integer)IDs.get(string)).intValue();
  }
  
  public void setOptionFloatValue(Options enumoptions, float f) {
    if (enumoptions == Options.BALL)
    {
      ball = f;
    }
    if (enumoptions == Options.CROSS)
    {
      cross = f;
    }
    if (enumoptions == Options.CIRCLE)
    {
      circle = f;
    }
  }
  
  public void setOptionValue(Options enumoptions, int i) {
    if (enumoptions == Options.PRECISION) {
      
      precision += i;
      if (precision > 6) precision = 0; 
    } 
    if (enumoptions == Options.HUD) {
      
      hud += i;
      if (hud > 3) hud = 0; 
    } 
  }
  
  public float getOptionFloatValue(Options enumoptions) {
    if (enumoptions == Options.BALL)
    {
      return ball;
    }
    if (enumoptions == Options.CROSS)
    {
      return cross;
    }
    if (enumoptions == Options.CIRCLE)
    {
      return circle;
    }
    return 0.0F;
  }
  
  public boolean getOptionOrdinalValue(Options enumoptions) {
    return false;
  }

  
  public String getKeyBinding(Options enumoptions) {
    String s = enumoptions.getEnumString() + ": ";
    if (enumoptions.getEnumFloat()) {
      
      float f = getOptionFloatValue(enumoptions);
      
      if (f == 0.0F)
      {
        return s + "options.off";
      }
      
      return s + (int)(f * 100.0F) + "%";
    } 
    
    if (!enumoptions.getEnumBoolean() || enumoptions == Options.PRECISION)
    {

      return s + PRECISION[precision];
    }
    if (enumoptions == Options.HUD)
    {
      return s + HUD[hud];
    }
    return s;
  }
  
  private static String getTranslation(String[] par0ArrayOfStr, int par1) {
    if (par1 < 0 || par1 >= par0ArrayOfStr.length)
    {
      par1 = 0;
    }
    
    return I18n.format(par0ArrayOfStr[par1], new Object[] { Integer.valueOf(par1) });
  }

  
  public void loadOptions() {
    try {
      if (!optionsFile.exists()) {
        return;
      }
      
      BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
      for (String s = ""; (s = bufferedreader.readLine()) != null;) {

        
        try {
          String[] as = s.split(":");
          if (as[0].equals("BallsRenderDistance"))
          {
            ball = parseFloat(as[1]);
          }
          if (as[0].equals("BallsRenderPrecision"))
          {
            precision = Integer.parseInt(as[1]);
          }
          if (as[0].equals("Hud"))
          {
            hud = Integer.parseInt(as[1]);
          }
          if (as[0].equals("KeyCrossSensitivity"))
          {
            cross = parseFloat(as[1]);
          }
          if (as[0].equals("KeyCircleSensitivity"))
          {
            circle = parseFloat(as[1]);
          }
        }
        catch (Exception e) {
          
          System.out.println("Skipping bad option: " + s);
          e.printStackTrace();
        } 
      } 
      bufferedreader.close();
      if (!idsFile.exists()) {
        return;
      }
      
      bufferedreader = new BufferedReader(new FileReader(idsFile));
      float version = 0.0F;
      for (String str1 = ""; (str1 = bufferedreader.readLine()) != null;) {

        
        try {
          String[] as = str1.split(":");
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
            if (as[0].equals(key)) {
              
              IDs.put(key, Integer.valueOf(Integer.parseInt(as[1])));
              if (textFields[i] != null) textFields[i].setText(as[1]); 
            } 
            i++;
          }
        
        } catch (Exception e) {
          
          System.out.println("Skipping bad option: " + str1);
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

  public void saveOptions() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
      printwriter.println("Version:" + SportsMod.OptionVersion());
      printwriter.println("BallsRenderDistance:" + ball);
      printwriter.println("BallsRenderPrecision:" + precision);
      printwriter.println("Hud:" + hud);
      printwriter.println("KeyCrossSensitivity:" + cross);
      printwriter.println("KeyCircleSensitivity:" + circle);
      printwriter.close();
    }
    catch (Exception exception) {
      
      System.out.println("Failed to save options");
      exception.printStackTrace();
    } 
  }

  
  public void saveIDs() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(idsFile));
      printwriter.println("Version:" + SportsMod.IDsVersion());
      Iterator<Integer> itValue = IDs.values().iterator();
      Iterator<String> itKey = IDs.keySet().iterator();
      int i = 0;
      while (itValue.hasNext()) {
        
        Integer value = itValue.next();
        String key = itKey.next();
        printwriter.println(key + ":" + textFields[i].getText().trim());
        i++;
      } 
      printwriter.close();
    }
    catch (Exception exception) {
      
      System.out.println("Failed to save options");
      exception.printStackTrace();
    } 
  }

  private static final String[] PRECISION = new String[] { "2D", "3D Cubic", "3D Spheric * 1", "3D Spheric * 2", "3D Spheric * 3", "3D Spheric * 4", "3D Spheric * 5", "3D Spheric * 6" };
  private static final String[] HUD = new String[] { "Full", "only Sport", "only Gauge", "Disabled" };
  public static int hud;
  public static int precision;
  public static float ball;
  public static float cross;
  public static float circle;
  protected Minecraft mc;
  private static File optionsFile;
  private static File idsFile;
  public boolean hideGUI;
  public int guiScale;
}