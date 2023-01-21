package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
  Day19 day = new Day19();

  @Test
  void testPart1() {
    assertEquals("179", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("9760485", day.part2().toString());
  }
}
