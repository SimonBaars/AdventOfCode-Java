package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year18.Day2018;

public class Day11 extends Day2018 {

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    return findSolution(false);
  }

  @Override
  public Object part2() {
    return findSolution(true);
  }

  private String findSolution(boolean part2) {
    long in = dayNumbers()[0];
    int size = 300;
    long[][] g = new long[size][size];
    long[][] summedArea = new long[size + 1][size + 1];
    
    // Calculate power levels
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        long rackId = x + 11;
        g[y][x] = get100Digit(((rackId * (y + 1)) + in) * rackId) - 5;
      }
    }
    
    // Build summed area table
    for (int y = 1; y <= size; y++) {
      for (int x = 1; x <= size; x++) {
        summedArea[y][x] = g[y-1][x-1] + summedArea[y-1][x] + summedArea[y][x-1] - summedArea[y-1][x-1];
      }
    }
    
    Pair<Long, Loc3D> top = new Pair<>(Long.MIN_VALUE, null);
    int maxSize = part2 ? size : 3;
    int minSize = part2 ? 1 : 3;
    
    // Find maximum sum using summed area table
    for (int s = minSize; s <= maxSize; s++) {
      for (int y = s; y <= size; y++) {
        for (int x = s; x <= size; x++) {
          long sum = summedArea[y][x] - summedArea[y-s][x] - summedArea[y][x-s] + summedArea[y-s][x-s];
          if (sum > top.a()) {
            top = new Pair<>(sum, new Loc3D(x-s+1, y-s+1, s));
          }
        }
      }
    }
    
    String res = top.b().toString().replace(" ", "");
    return part2 ? res : res.substring(0, res.length() - 2);
  }

  public long get100Digit(long n) {
    return n > 100 ? (n / 100) % 10 : 0;
  }
}
