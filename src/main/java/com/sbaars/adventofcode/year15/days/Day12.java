package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Day2015 {

  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    Day12 day = new Day12();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 12, 1);
    new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 12, 2);
  }

  @Override
  public Object part1() {
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher matcher = pattern.matcher(day());
    int sum = 0;
    while (matcher.find()) {
      sum += Integer.parseInt(matcher.group());
    }
    return sum;
  }

  @Override
  public Object part2() {
    return sumJson(day());
  }

  private int sumJson(String json) {
    if (json.startsWith("[")) {
      return sumArray(json);
    } else if (json.startsWith("{")) {
      return sumObject(json);
    } else {
      try {
        return Integer.parseInt(json);
      } catch (NumberFormatException e) {
        return 0;
      }
    }
  }

  private int sumArray(String json) {
    int sum = 0;
    int depth = 0;
    int start = 1;
    boolean inString = false;
    
    for (int i = 1; i < json.length() - 1; i++) {
      char c = json.charAt(i);
      if (c == '"') {
        inString = !inString;
      }
      if (inString) continue;
      
      if (c == '[' || c == '{') {
        depth++;
      } else if (c == ']' || c == '}') {
        depth--;
      } else if (c == ',' && depth == 0) {
        sum += sumJson(json.substring(start, i).trim());
        start = i + 1;
      }
    }
    if (start < json.length() - 1) {
      sum += sumJson(json.substring(start, json.length() - 1).trim());
    }
    return sum;
  }

  private int sumObject(String json) {
    // Check if object contains "red" as a value
    Pattern redPattern = Pattern.compile(":\\s*\"red\"");
    Matcher redMatcher = redPattern.matcher(json);
    if (redMatcher.find()) {
      return 0;
    }

    int sum = 0;
    int depth = 0;
    int start = 1;
    boolean inString = false;
    
    for (int i = 1; i < json.length() - 1; i++) {
      char c = json.charAt(i);
      if (c == '"') {
        inString = !inString;
      }
      if (inString) continue;
      
      if (c == '[' || c == '{') {
        depth++;
      } else if (c == ']' || c == '}') {
        depth--;
      } else if (c == ',' && depth == 0) {
        String part = json.substring(start, i).trim();
        int colonIndex = part.indexOf(':');
        if (colonIndex != -1) {
          sum += sumJson(part.substring(colonIndex + 1).trim());
        }
        start = i + 1;
      }
    }
    if (start < json.length() - 1) {
      String part = json.substring(start, json.length() - 1).trim();
      int colonIndex = part.indexOf(':');
      if (colonIndex != -1) {
        sum += sumJson(part.substring(colonIndex + 1).trim());
      }
    }
    return sum;
  }
}
