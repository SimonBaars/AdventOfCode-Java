package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.ArrayList;
import java.util.List;

public class Day24 extends Day2021 implements HasRecursion {
  private final int[] divZ = new int[14];
  private final int[] addX = new int[14];
  private final int[] addY = new int[14];

  public Day24() {
    super(24);
    parseInput();
  }

  private void parseInput() {
    String[] lines = dayStream().toArray(String[]::new);
    for (int i = 0; i < 14; i++) {
      divZ[i] = Integer.parseInt(lines[i * 18 + 4].split(" ")[2]);
      addX[i] = Integer.parseInt(lines[i * 18 + 5].split(" ")[2]);
      addY[i] = Integer.parseInt(lines[i * 18 + 15].split(" ")[2]);
    }
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  @Override
  public Object part1() {
    return findModelNumber(true);
  }

  @Override
  public Object part2() {
    return findModelNumber(false);
  }

  private String findModelNumber(boolean max) {
    List<int[]> pairs = new ArrayList<>();
    int[] stack = new int[14];
    int stackPtr = 0;

    // Find digit pairs based on z operations
    for (int i = 0; i < 14; i++) {
      if (divZ[i] == 1) {
        stack[stackPtr++] = i;
      } else {
        int j = stack[--stackPtr];
        pairs.add(new int[]{j, i});
      }
    }

    int[] digits = new int[14];
    // Process pairs to find valid digits
    for (int[] pair : pairs) {
      int i = pair[0], j = pair[1];
      int diff = addY[i] + addX[j];
      
      if (max) {
        digits[i] = Math.min(9, 9 - diff);
        digits[j] = Math.min(9, 9 + diff);
      } else {
        digits[i] = Math.max(1, 1 - diff);
        digits[j] = Math.max(1, 1 + diff);
      }
    }

    StringBuilder result = new StringBuilder();
    for (int digit : digits) {
      result.append(digit);
    }
    return result.toString();
  }
}
