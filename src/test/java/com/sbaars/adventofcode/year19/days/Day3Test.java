package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
  Day3 day = new Day3();

  @Test
  void testPart1() {
    assertEquals("303", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("11222", day.part2().toString());
  }
}
