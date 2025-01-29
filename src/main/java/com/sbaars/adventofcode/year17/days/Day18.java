package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.HashMap;
import java.util.Map;

public class Day18 extends Day2017 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  @Override
  public Object part1() {
    String[] instructions = dayStrings();
    Map<String, Long> registers = new HashMap<>();
    long lastSound = 0;
    int ip = 0;  // instruction pointer

    while (ip >= 0 && ip < instructions.length) {
      String[] parts = instructions[ip].split(" ");
      String cmd = parts[0];
      String x = parts[1];
      String y = parts.length > 2 ? parts[2] : null;

      long xVal = getValue(registers, x);
      long yVal = y != null ? getValue(registers, y) : 0;

      switch (cmd) {
        case "snd":
          lastSound = xVal;
          break;
        case "set":
          registers.put(x, yVal);
          break;
        case "add":
          registers.put(x, xVal + yVal);
          break;
        case "mul":
          registers.put(x, xVal * yVal);
          break;
        case "mod":
          registers.put(x, xVal % yVal);
          break;
        case "rcv":
          if (xVal != 0) {
            return lastSound;
          }
          break;
        case "jgz":
          if (xVal > 0) {
            ip += yVal - 1;  // -1 because we'll add 1 at the end
          }
          break;
      }

      ip++;
    }

    return lastSound;
  }

  private long getValue(Map<String, Long> registers, String value) {
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return registers.getOrDefault(value, 0L);
    }
  }

  @Override
  public Object part2() {
    return 0;
  }
}
