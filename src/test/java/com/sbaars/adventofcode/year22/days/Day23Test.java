package com.sbaars.adventofcode.year22.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Test {
  Day23 day = new Day23();

  @Test
  void testPart1() {
    assertEquals("4195", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("1069", day.part2().toString());
  }
}
