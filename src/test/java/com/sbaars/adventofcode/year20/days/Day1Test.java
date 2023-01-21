package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
  Day1 day = new Day1();

  @Test
  void testPart1() {
    assertEquals("270144", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("261342720", day.part2().toString());
  }
}
