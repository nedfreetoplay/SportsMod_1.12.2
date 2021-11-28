package net.sports.client.settings;

public enum Options
{
  BALL("Ball", 0, "Balls render distance", true, false),
  PRECISION("PRECISION", 1, "Balls render", false, false),
  CROSS("Cross", 2, "Key Cross Sensitivity", true, false),
  CIRCLE("Circle", 3, "Key Circle Sensitivity", true, false),
  HUD("HUD", 4, "HUD Display", false, false);
  private final boolean enumFloat;
  
  public static Options getOptions(int i) {
    Options[] aenumoptions = values();
    int j = aenumoptions.length;
    for (int k = 0; k < j; k++) {
      
      Options enumoptions = aenumoptions[k];
      if (enumoptions.returnEnumOrdinal() == i)
      {
        return enumoptions;
      }
    } 
    return null;
  }
  private final boolean enumBoolean; private final String enumString;
  
  Options(String s, int i, String s1, boolean flag, boolean flag1) {
    this.enumString = s1;
    this.enumFloat = flag;
    this.enumBoolean = flag1;
  }

  
  public boolean getEnumFloat() {
    return this.enumFloat;
  }

  
  public boolean getEnumBoolean() {
    return this.enumBoolean;
  }

  
  public int returnEnumOrdinal() {
    return ordinal();
  }

  
  public String getEnumString() {
    return this.enumString;
  }
}