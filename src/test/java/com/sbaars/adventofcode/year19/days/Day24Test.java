  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day24;

  class Day24Test {
      Day24 day = new Day24();

      @Test
      void testPart1() {
          assertEquals("32506911", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2025", day.part2().toString());
      }
  }
