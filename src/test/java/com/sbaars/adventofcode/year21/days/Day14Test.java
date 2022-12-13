  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day14;

  class Day14Test {
      Day14 day = new Day14();

      @Test
      void testPart1() {
          assertEquals("3284", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("4302675529689", day.part2().toString());
      }
  }
