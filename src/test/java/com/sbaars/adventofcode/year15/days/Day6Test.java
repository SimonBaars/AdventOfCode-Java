package com.sbaars.adventofcode.year15.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
  Day6 day = new Day6();

  @Test
  void testPart1() {
    assertEquals("569999", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("17836115", day.part2().toString());
  }
}
