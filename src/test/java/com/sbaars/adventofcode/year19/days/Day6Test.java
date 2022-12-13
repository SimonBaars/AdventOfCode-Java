  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day6;

  class Day6Test {
      Day6 day = new Day6();

      @Test
      void testPart1() {
          assertEquals("186597", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("412", day.part2().toString());
      }
  }
