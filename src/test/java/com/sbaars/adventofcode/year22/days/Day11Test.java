  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day11;

  class Day11Test {
      Day11 day = new Day11();

      @Test
      void testPart1() {
          assertEquals("108240", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("25712998901", day.part2().toString());
      }
  }
