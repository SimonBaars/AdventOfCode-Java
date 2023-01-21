package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
  Day7 day = new Day7();

  @Test
  void testPart1() {
    assertEquals("116680", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("89603079", day.part2().toString());
  }
}
