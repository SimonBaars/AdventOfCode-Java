package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {
  Day14 day = new Day14();

  @Test
  void testPart1() {
    assertEquals("14722016054794", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("3618217244644", day.part2().toString());
  }
}
