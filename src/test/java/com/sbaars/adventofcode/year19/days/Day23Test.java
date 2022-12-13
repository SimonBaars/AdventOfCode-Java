  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day23;

  class Day23Test {
      Day23 day = new Day23();

      @Test
      void testPart1() {
          assertEquals("17286", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("11249", day.part2().toString());
      }
  }
