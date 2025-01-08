package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends Day2016 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  private int getValue(String x, Map<String, Integer> registers) {
    try {
      return Integer.parseInt(x);
    } catch (NumberFormatException e) {
      return registers.getOrDefault(x, 0);
    }
  }

  private int executeProgram(List<String> instructions) {
    Map<String, Integer> registers = new HashMap<>();
    int pc = 0;

    while (pc < instructions.size()) {
      String[] parts = instructions.get(pc).split(" ");
      String op = parts[0];

      switch (op) {
        case "cpy" -> {
          registers.put(parts[2], getValue(parts[1], registers));
          pc++;
        }
        case "inc" -> {
          registers.put(parts[1], registers.getOrDefault(parts[1], 0) + 1);
          pc++;
        }
        case "dec" -> {
          registers.put(parts[1], registers.getOrDefault(parts[1], 0) - 1);
          pc++;
        }
        case "jnz" -> {
          if (getValue(parts[1], registers) != 0) {
            pc += Integer.parseInt(parts[2]);
          } else {
            pc++;
          }
        }
      }
    }

    return registers.getOrDefault("a", 0);
  }

  @Override
  public Object part1() {
    return executeProgram(dayStream().toList());
  }

  @Override
  public Object part2() {
    return "";
  }
}
