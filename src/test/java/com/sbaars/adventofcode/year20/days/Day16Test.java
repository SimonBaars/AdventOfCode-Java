package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {
  Day16 day = new Day16();

  @Test
  void testPart1() {
    assertEquals("26988", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("426362917709", day.part2().toString());
  }
}
