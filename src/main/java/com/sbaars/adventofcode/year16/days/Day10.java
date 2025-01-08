package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day2016 {
  private static final Pattern VALUE_PATTERN = Pattern.compile("value (\\d+) goes to bot (\\d+)");
  private static final Pattern BOT_PATTERN = Pattern.compile("bot (\\d+) gives low to (output|bot) (\\d+) and high to (output|bot) (\\d+)");

  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  private static class Bot {
    List<Integer> chips = new ArrayList<>();
    String lowType;
    int lowId;
    String highType;
    int highId;
  }

  @Override
  public Object part1() {
    Map<Integer, Bot> bots = new HashMap<>();
    List<String> instructions = dayStream().toList();

    // First pass: Create bots and set their instructions
    for (String instruction : instructions) {
      Matcher botMatcher = BOT_PATTERN.matcher(instruction);
      if (botMatcher.matches()) {
        int botId = Integer.parseInt(botMatcher.group(1));
        Bot bot = bots.computeIfAbsent(botId, k -> new Bot());
        bot.lowType = botMatcher.group(2);
        bot.lowId = Integer.parseInt(botMatcher.group(3));
        bot.highType = botMatcher.group(4);
        bot.highId = Integer.parseInt(botMatcher.group(5));
      }
    }

    // Second pass: Give initial values to bots
    for (String instruction : instructions) {
      Matcher valueMatcher = VALUE_PATTERN.matcher(instruction);
      if (valueMatcher.matches()) {
        int value = Integer.parseInt(valueMatcher.group(1));
        int botId = Integer.parseInt(valueMatcher.group(2));
        bots.computeIfAbsent(botId, k -> new Bot()).chips.add(value);
      }
    }

    // Process bots until we find the one comparing 61 and 17
    Map<Integer, List<Integer>> outputs = new HashMap<>();
    while (true) {
      for (Map.Entry<Integer, Bot> entry : bots.entrySet()) {
        Bot bot = entry.getValue();
        if (bot.chips.size() == 2) {
          Collections.sort(bot.chips);
          if (bot.chips.get(0) == 17 && bot.chips.get(1) == 61) {
            return entry.getKey();
          }

          // Give low value
          if (bot.lowType.equals("bot")) {
            bots.get(bot.lowId).chips.add(bot.chips.get(0));
          } else {
            outputs.computeIfAbsent(bot.lowId, k -> new ArrayList<>()).add(bot.chips.get(0));
          }

          // Give high value
          if (bot.highType.equals("bot")) {
            bots.get(bot.highId).chips.add(bot.chips.get(1));
          } else {
            outputs.computeIfAbsent(bot.highId, k -> new ArrayList<>()).add(bot.chips.get(1));
          }

          bot.chips.clear();
        }
      }
    }
  }

  @Override
  public Object part2() {
    return "";
  }
}
