package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day2016 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  private int getValue(String x, Map<String, Integer> registers) {
    try {
      return Integer.parseInt(x);
    } catch (NumberFormatException e) {
      return registers.getOrDefault(x, 0);
    }
  }

  private void toggle(List<String> instructions, int index) {
    if (index < 0 || index >= instructions.size()) {
      return;
    }

    String[] parts = instructions.get(index).split(" ");
    String newInstruction;

    if (parts.length == 2) {
      // One-argument instruction
      newInstruction = parts[0].equals("inc") ? "dec" : "inc";
    } else {
      // Two-argument instruction
      newInstruction = parts[0].equals("jnz") ? "cpy" : "jnz";
    }

    instructions.set(index, newInstruction + instructions.get(index).substring(parts[0].length()));
  }

  private int executeProgram(List<String> instructions, int initialA) {
    Map<String, Integer> registers = new HashMap<>();
    registers.put("a", initialA);
    int pc = 0;

    while (pc < instructions.size()) {
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
        case "tgl" -> {
          toggle(instructions, pc + getValue(parts[1], registers));
          pc++;
        }
        default -> pc++;
      }
    }

    return registers.get("a");
  }

  @Override
  public Object part1() {
    List<String> instructions = new ArrayList<>(dayStream().toList());
    return executeProgram(instructions, 7);
  }

  @Override
  public Object part2() {
    return "";
  }
}
