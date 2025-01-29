package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day11 extends Day2017 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
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
    
    // Distance in hex grid is max absolute value of coordinates
    return Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(z)));
  }

  @Override
  public Object part2() {
    return "";
  }
}
