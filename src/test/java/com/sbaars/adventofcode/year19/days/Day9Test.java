package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {
  Day9 day = new Day9();

  @Test
  void testPart1() {
    assertEquals("2518058886", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("44292", day.part2().toString());
  }
}
