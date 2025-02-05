package pl.acmc.media.utils;

public class NumberUtil {
  public static Integer parseInt(String raw) {
    try {
      return Integer.valueOf(raw);
    } catch (Exception ignored) {
      return null;
    } 
  }
  
  public static boolean canParseInt(String raw) {
    return (parseInt(raw) != null);
  }
  
  public static Double parseDouble(String raw) {
    try {
      Double aDouble = Double.valueOf(raw);
      if (aDouble.isNaN())
        return null; 
      return aDouble;
    } catch (Exception ignored) {
      return null;
    } 
  }
  
  public static boolean hasDecimal(double d) {
    return (d % 1.0D != 0.0D);
  }
  
  public static double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException(); 
    long factor = (long)Math.pow(10.0D, places);
    value *= factor;
    long tmp = Math.round(value);
    return tmp / factor;
  }
  
  public static float round(float value, int places) {
    if (places < 0)
      throw new IllegalArgumentException(); 
    long factor = (long)Math.pow(10.0D, places);
    value *= (float)factor;
    long tmp = Math.round(value);
    return (float)tmp / (float)factor;
  }
  
  public static double getKD(int kills, int deaths) {
    if (kills == 0 && deaths == 0)
      return 0.0D; 
    if (kills > 0 && deaths == 0)
      return kills; 
    if (kills == 0 && deaths > 0)
      return -deaths; 
    return round(kills / deaths, 3);
  }
  
  public static int generateRandomNumber(int size) {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < size; i++)
      s.append(MathUtil.getRandInt((i > 0) ? 0 : 1, 9));
    return Integer.parseInt(s.toString());
  }
}