package com.sbaars.adventofcode.template.days;

import com.sbaars.adventofcode.template.Day1999;

public class Day1 extends Day1999 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    String input = day();
    return input;
  }

  @Override
  public Object part2() {
    return 0;
  }
}
