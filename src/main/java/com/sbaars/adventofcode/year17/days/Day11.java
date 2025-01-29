package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day11 extends Day2017 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  private int calculateDistance(int x, int y, int z) {
    return Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(z)));
  }

  @Override
  public Object part1() {
    String[] steps = day().trim().split(",");
    // Using cube coordinates (x, y, z) where x + y + z = 0
    int x = 0, y = 0, z = 0;
    
    for (String step : steps) {
      switch (step) {
        case "n" -> { y++; z--; }
        case "s" -> { y--; z++; }
        case "ne" -> { x++; z--; }
        case "sw" -> { x--; z++; }
        case "nw" -> { x--; y++; }
        case "se" -> { x++; y--; }
      }
    }
    
    return calculateDistance(x, y, z);
  }

  @Override
  public Object part2() {
    String[] steps = day().trim().split(",");
    int x = 0, y = 0, z = 0;
    int maxDistance = 0;
    
    for (String step : steps) {
      switch (step) {
        case "n" -> { y++; z--; }
        case "s" -> { y--; z++; }
        case "ne" -> { x++; z--; }
        case "sw" -> { x--; z++; }
        case "nw" -> { x--; y++; }
        case "se" -> { x++; y--; }
      }
      maxDistance = Math.max(maxDistance, calculateDistance(x, y, z));
    }
    
    return maxDistance;
  }
}
