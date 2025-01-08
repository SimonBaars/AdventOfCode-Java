package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25 extends Day2016 {
  private static final int MAX_OUTPUTS = 10;

  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  private int getValue(String x, Map<String, Integer> registers) {
    try {
      return Integer.parseInt(x);
    } catch (NumberFormatException e) {
      return registers.getOrDefault(x, 0);
    }
  }

  private boolean isValidOutput(List<Integer> outputs) {
    if (outputs.size() < MAX_OUTPUTS) return false;
    for (int i = 0; i < outputs.size(); i++) {
      if (outputs.get(i) != i % 2) return false;
    }
    return true;
  }

  private boolean testValue(List<String> instructions, int initialA) {
    Map<String, Integer> registers = new HashMap<>();
    registers.put("a", initialA);
    int pc = 0;
    List<Integer> outputs = new ArrayList<>();

    while (pc < instructions.size() && outputs.size() < MAX_OUTPUTS) {
      String[] parts = instructions.get(pc).split(" ");
      String op = parts[0];

      switch (op) {
        case "cpy" -> {
          if (parts[2].matches("[a-d]")) {
            registers.put(parts[2], getValue(parts[1], registers));
          }
          pc++;
        }
        case "inc" -> {
          if (parts[1].matches("[a-d]")) {
            registers.put(parts[1], registers.getOrDefault(parts[1], 0) + 1);
          }
          pc++;
        }
        case "dec" -> {
          if (parts[1].matches("[a-d]")) {
            registers.put(parts[1], registers.getOrDefault(parts[1], 0) - 1);
          }
          pc++;
        }
        case "jnz" -> {
          if (getValue(parts[1], registers) != 0) {
            pc += getValue(parts[2], registers);
          } else {
            pc++;
          }
        }
        case "out" -> {
          outputs.add(getValue(parts[1], registers));
          pc++;
        }
        default -> pc++;
      }
    }

    return isValidOutput(outputs);
  }

  @Override
  public Object part1() {
    List<String> instructions = new ArrayList<>(dayStream().toList());
    int a = 1;
    while (!testValue(instructions, a)) {
      a++;
    }
    return a;
  }

  @Override
  public Object part2() {
    return "Merry Christmas!";
  }
}
