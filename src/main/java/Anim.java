package net.sports;

public enum Anim
{
  INIT,
  GLOBAL, ARMER_TIR, TIR, ARMER_PASSE, PASSE, INHAND,
  BASKETBALL, SPINNING,
  FOOTBALL, ARRET,
  TENNIS,
  BASEBALL, CATCH, KNOCK,
  RESET;
  private static final Anim[] anims;
  public static final Anim get(int i) { return anims[i]; } static {
    anims = values();
  }
}