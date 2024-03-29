package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {
  Day17 day = new Day17();

  @Test
  void testPart1() {
    assertEquals("8911", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("4748", day.part2().toString());
  }
}
