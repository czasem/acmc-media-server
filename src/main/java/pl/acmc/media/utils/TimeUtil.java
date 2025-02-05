package pl.acmc.media.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
  private static final Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);


  public static String convert(long millis, boolean showSeconds) {
    return convert(millis, showSeconds, false);
  }

  public static String convert(long millis) {
    return convert(millis, true, false);
  }

  public static String convert(long millis, boolean showSeconds, boolean showMillis) {
    if (millis < 1000L)
      return showMillis ? millis+"ms" : "< 1s";
    long days = TimeUnit.MILLISECONDS.toDays(millis);
    millis -= TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    millis -= TimeUnit.SECONDS.toMillis(seconds);
    StringBuilder sb = new StringBuilder();
    if (days > 0L)
      sb.append(days).append("d");
    if (hours > 0L) {
      if (days > 0L)
        sb.append(" ");
      sb.append(hours).append("h");
    }
    if (minutes > 0L) {
      if (hours > 0L || days > 0L)
        sb.append(" ");
      sb.append(minutes).append("m");
    }
    if ((showSeconds || sb.length() == 0) && seconds > 0L) {
      if (minutes > 0L || hours > 0L || days > 0L)
        sb.append(" ");
      sb.append(seconds).append("s");
    }
    if (showMillis && millis > 0L) {
      if (seconds > 0L || minutes > 0L || hours > 0L || days > 0L)
        sb.append(" ");
      sb.append(millis).append("ms");
    }
    return sb.toString();
  }

  public static String convertPolish(long millis) {
    return convertPolish(millis, true);
  }

  public static String convertPolish(long millis, boolean showSeconds) {
    if (millis < 1000L) {
      return millis + " ms";
    }

    long yearMillis = 1000L * 60 * 60 * 24 * 365;
    long monthMillis = 1000L * 60 * 60 * 24 * 30;

    long years = millis / yearMillis;
    millis -= years * yearMillis;

    long months = millis / monthMillis;
    millis -= months * monthMillis;

    long days = TimeUnit.MILLISECONDS.toDays(millis);
    millis -= TimeUnit.DAYS.toMillis(days);

    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);

    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);

    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    StringBuilder sb = new StringBuilder();

    if (years > 0L) {
      sb.append(years).append(" ").append(formatUnit(years, "rok", "lata", "lat"));
    }
    if (months > 0L) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(months).append(" ").append(formatUnit(months, "miesiąc", "miesiące", "miesięcy"));
    }
    if (days > 0L) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(days).append(" ").append(formatUnit(days, "dzień", "dni", "dni"));
    }
    if (hours > 0L) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(hours).append(" ").append(formatUnit(hours, "godzina", "godziny", "godzin"));
    }
    if (minutes > 0L) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(minutes).append(" ").append(formatUnit(minutes, "minuta", "minuty", "minut"));
    }
    if ((showSeconds || sb.length() == 0) && seconds > 0L) {
      if (sb.length() > 0) sb.append(" ");
      sb.append(seconds).append(" ").append(formatUnit(seconds, "sekunda", "sekundy", "sekund"));
    }

    return sb.toString();
  }

  private static String formatUnit(long value, String singular, String few, String many) {
    if (value == 1) {
      return singular;
    } else if (value >= 2 && value <= 4) {
      return few;
    } else {
      return many;
    }
  }
  
  public static String getDate(long data) {
    return (new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")).format(Long.valueOf(data));
  }
  
  public static long parseDateDiff(String time, boolean future) {
    try {
      Matcher m = timePattern.matcher(time);
      int years = 0;
      int months = 0;
      int weeks = 0;
      int days = 0;
      int hours = 0;
      int minutes = 0;
      int seconds = 0;
      boolean found = false;
      while (m.find()) {
        if (m.group() == null || m.group().isEmpty())
          continue; 
        for (int i = 0; i < m.groupCount(); i++) {
          if (m.group(i) != null && !m.group(i).isEmpty()) {
            found = true;
            break;
          } 
        } 
        if (found) {
          if (m.group(1) != null && !m.group(1).isEmpty())
            years = Integer.parseInt(m.group(1)); 
          if (m.group(2) != null && !m.group(2).isEmpty())
            months = Integer.parseInt(m.group(2)); 
          if (m.group(3) != null && !m.group(3).isEmpty())
            weeks = Integer.parseInt(m.group(3)); 
          if (m.group(4) != null && !m.group(4).isEmpty())
            days = Integer.parseInt(m.group(4)); 
          if (m.group(5) != null && !m.group(5).isEmpty())
            hours = Integer.parseInt(m.group(5)); 
          if (m.group(6) != null && !m.group(6).isEmpty())
            minutes = Integer.parseInt(m.group(6)); 
          if (m.group(7) != null && !m.group(7).isEmpty())
            seconds = Integer.parseInt(m.group(7)); 
          break;
        } 
      } 
      if (!found)
        return -1L; 
      Calendar c = new GregorianCalendar();
      if (years > 0)
        c.add(1, years); 
      if (months > 0)
        c.add(2, months); 
      if (weeks > 0)
        c.add(3, weeks); 
      if (days > 0)
        c.add(5, days); 
      if (hours > 0)
        c.add(11, hours); 
      if (minutes > 0)
        c.add(12, minutes); 
      if (seconds > 0)
        c.add(13, seconds); 
      return c.getTimeInMillis() - (future ? 0L : System.currentTimeMillis());
    } catch (Exception e) {
      return -1L;
    } 
  }

  public static long deserialize(String timeString) {
    long totalMillis = 0L;

    Pattern pattern = Pattern.compile("(\\d+)([dhms])");
    Matcher matcher = pattern.matcher(timeString);

    while (matcher.find()) {
      int value = Integer.parseInt(matcher.group(1));
      String unit = matcher.group(2);

      switch (unit) {
        case "d":
          totalMillis += TimeUnit.DAYS.toMillis(value);
          break;
        case "h":
          totalMillis += TimeUnit.HOURS.toMillis(value);
          break;
        case "m":
          totalMillis += TimeUnit.MINUTES.toMillis(value);
          break;
        case "s":
          totalMillis += TimeUnit.SECONDS.toMillis(value);
          break;
        default:
          throw new IllegalArgumentException("Unknown time unit: " + unit);
      }
    }
    return totalMillis;
  }

  public static long parseDate(String data) {
    try {
      return (new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")).parse(data).getTime();
    } catch (ParseException e) {
      return 0L;
    } 
  }
}