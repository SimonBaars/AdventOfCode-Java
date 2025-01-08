package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

public class Day2 extends Day2016 {
  private static final int[][] KEYPAD = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
  };

  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  @Override
  public Object part1() {
    int x = 1, y = 1; // Starting at 5 (center)
    StringBuilder code = new StringBuilder();
    
    for (String line : dayStream().toList()) {
      for (char move : line.toCharArray()) {
        switch (move) {
          case 'U' -> y = Math.max(0, y - 1);
          case 'D' -> y = Math.min(2, y + 1);
          case 'L' -> x = Math.max(0, x - 1);
          case 'R' -> x = Math.min(2, x + 1);
        }
      }
      code.append(KEYPAD[y][x]);
    }
    
    return code.toString();
  }

  @Override
  public Object part2() {
    return "";
  }
}
