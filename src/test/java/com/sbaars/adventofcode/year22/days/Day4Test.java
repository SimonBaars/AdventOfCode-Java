package com.sbaars.adventofcode.year22.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
  Day4 day = new Day4();

  @Test
  void testPart1() {
    assertEquals("485", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("857", day.part2().toString());
  }
}
