package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
  Day6 day = new Day6();

  @Test
  void testPart1() {
    assertEquals("352151", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("1601616884019", day.part2().toString());
  }
}
