package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.Arrays;

public class Day3 extends Day2016 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  private boolean isValidTriangle(int a, int b, int c) {
    return a + b > c && b + c > a && a + c > b;
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(line -> Arrays.stream(line.trim().split("\\s+"))
            .mapToInt(Integer::parseInt)
            .toArray())
        .filter(sides -> isValidTriangle(sides[0], sides[1], sides[2]))
        .count();
  }

  @Override
  public Object part2() {
    return "";
  }
}
