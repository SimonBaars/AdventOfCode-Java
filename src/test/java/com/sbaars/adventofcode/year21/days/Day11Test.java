  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day11;

  class Day11Test {
      Day11 day = new Day11();

      @Test
      void testPart1() {
          assertEquals("1594", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("437", day.part2().toString());
      }
  }
