  package com.sbaars.adventofcode.year18.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year18.days.Day3;

  class Day3Test {
      Day3 day = new Day3();

      @Test
      void testPart1() {
          assertEquals("103482", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("686", day.part2().toString());
      }
  }
