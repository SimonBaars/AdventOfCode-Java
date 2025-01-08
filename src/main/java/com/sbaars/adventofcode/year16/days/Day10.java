package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day2016 {
  private static final Pattern VALUE_PATTERN = Pattern.compile("value (\\d+) goes to bot (\\d+)");
  private static final Pattern GIVE_PATTERN = Pattern.compile("bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)");

  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  private record Instruction(int botId, String lowType, int lowId, String highType, int highId) {}
  private record Result(int targetBot, int outputProduct) {}
  private record Transfer(String type, int id, int value) {}

  private Result simulateBots(int targetLow, int targetHigh) {
    Map<Integer, List<Integer>> bots = new HashMap<>();
    Map<Integer, Integer> outputs = new HashMap<>();
    Map<Integer, Instruction> instructions = new HashMap<>();
    List<String> input = dayStream().toList();

    // Parse instructions
    for (String line : input) {
      Matcher valueMatcher = VALUE_PATTERN.matcher(line);
      Matcher giveMatcher = GIVE_PATTERN.matcher(line);

      if (valueMatcher.matches()) {
        int value = Integer.parseInt(valueMatcher.group(1));
        int bot = Integer.parseInt(valueMatcher.group(2));
        bots.computeIfAbsent(bot, k -> new ArrayList<>()).add(value);
      } else if (giveMatcher.matches()) {
        int bot = Integer.parseInt(giveMatcher.group(1));
        String lowType = giveMatcher.group(2);
        int lowId = Integer.parseInt(giveMatcher.group(3));
        String highType = giveMatcher.group(4);
        int highId = Integer.parseInt(giveMatcher.group(5));
        instructions.put(bot, new Instruction(bot, lowType, lowId, highType, highId));
      }
    }

    // Process bots
    int targetBot = -1;
    while (true) {
      List<Transfer> transfers = new ArrayList<>();
      List<Integer> botsToProcess = new ArrayList<>();

      for (Map.Entry<Integer, List<Integer>> entry : bots.entrySet()) {
        if (entry.getValue().size() == 2) {
          botsToProcess.add(entry.getKey());
        }
      }

      if (botsToProcess.isEmpty()) break;

      for (int botId : botsToProcess) {
        List<Integer> chips = bots.get(botId);
        int low = Math.min(chips.get(0), chips.get(1));
        int high = Math.max(chips.get(0), chips.get(1));

        if (low == targetLow && high == targetHigh) {
          targetBot = botId;
        }

        Instruction inst = instructions.get(botId);
        transfers.add(new Transfer(inst.lowType, inst.lowId, low));
        transfers.add(new Transfer(inst.highType, inst.highId, high));
        chips.clear();
      }

      // Apply transfers
      for (Transfer t : transfers) {
        if (t.type.equals("bot")) {
          bots.computeIfAbsent(t.id, k -> new ArrayList<>()).add(t.value);
        } else {
          outputs.put(t.id, t.value);
        }
      }
    }

    int product = outputs.containsKey(0) && outputs.containsKey(1) && outputs.containsKey(2) ?
        outputs.get(0) * outputs.get(1) * outputs.get(2) : 0;

    return new Result(targetBot, product);
  }

  @Override
  public Object part1() {
    return simulateBots(17, 61).targetBot;
  }

  @Override
  public Object part2() {
    return simulateBots(17, 61).outputProduct;
  }
}
