package com.sbaars.adventofcode.year15.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
  Day7 day = new Day7();

  @Test
  void testPart1() {
    assertEquals("46065", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("14134", day.part2().toString());
  }
}
