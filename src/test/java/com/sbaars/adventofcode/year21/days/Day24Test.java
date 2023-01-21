package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Test {
  Day24 day = new Day24();

  @Test
  void testPart1() {
    assertEquals("99799212949967", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("34198111816311", day.part2().toString());
  }
}
