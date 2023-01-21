package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
  Day11 day = new Day11();

  @Test
  void testPart1() {
    assertEquals("1594", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("437", day.part2().toString());
  }
}
