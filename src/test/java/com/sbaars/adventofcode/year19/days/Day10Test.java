  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day10;

  class Day10Test {
      Day10 day = new Day10();

      @Test
      void testPart1() {
          assertEquals("299", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("1419", day.part2().toString());
      }
  }
