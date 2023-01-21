package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Test {
  Day23 day = new Day23();

  @Test
  void testPart1() {
    assertEquals("17286", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("11249", day.part2().toString());
  }
}
