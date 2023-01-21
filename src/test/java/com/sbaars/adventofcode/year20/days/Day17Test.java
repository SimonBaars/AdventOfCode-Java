package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {
  Day17 day = new Day17();

  @Test
  void testPart1() {
    assertEquals("295", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("1972", day.part2().toString());
  }
}
