package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
  Day13 day = new Day13();

  @Test
  void testPart1() {
    assertEquals("2935", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("836024966345345", day.part2().toString());
  }
}
