package net.sports;

public enum Sports
{
  ATHLETISME,
  BASKETBALL,
  FOOTBALL,
  TENNIS,
  BASEBALL;
  private static final Sports[] sports;
  public static final Sports get(int i) { return sports[i]; } static {
    sports = values();
  }
}