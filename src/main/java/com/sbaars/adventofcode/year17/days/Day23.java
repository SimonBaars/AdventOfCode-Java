package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.HashMap;
import java.util.Map;

public class Day23 extends Day2017 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  private long getValue(Map<String, Long> registers, String value) {
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return registers.getOrDefault(value, 0L);
    }
  }

  @Override
  public Object part1() {
    String[] instructions = dayStrings();
    Map<String, Long> registers = new HashMap<>();
    int ip = 0;  // instruction pointer
    int mulCount = 0;

    while (ip >= 0 && ip < instructions.length) {
      String[] parts = instructions[ip].split(" ");
      String cmd = parts[0];
      String x = parts[1];
      String y = parts[2];

      long xVal = getValue(registers, x);
      long yVal = getValue(registers, y);

      switch (cmd) {
        case "set":
          registers.put(x, yVal);
          break;
        case "sub":
          registers.put(x, xVal - yVal);
          break;
        case "mul":
          registers.put(x, xVal * yVal);
          mulCount++;
          break;
        case "jnz":
          if (xVal != 0) {
            ip += yVal - 1;  // -1 because we'll add 1 at the end
          }
          break;
      }

      ip++;
    }

    return mulCount;
  }

  @Override
  public Object part2() {
    // When a = 1, the program essentially counts non-prime numbers in a range
    // b starts at 67 * 100 + 100000 = 106700
    // c is b + 17000 = 123700
    // The program increments h for each non-prime number between b and c, stepping by 17
    int b = 106700;
    int c = 123700;
    int h = 0;

    for (int n = b; n <= c; n += 17) {
      // Check if n is not prime
      boolean isPrime = true;
      for (int d = 2; d <= Math.sqrt(n); d++) {
        if (n % d == 0) {
          isPrime = false;
          break;
        }
      }
      if (!isPrime) {
        h++;
      }
    }

    return h;
  }
}
