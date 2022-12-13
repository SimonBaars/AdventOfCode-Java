  package com.sbaars.adventofcode.year20.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year20.days.Day11;

  class Day11Test {
      Day11 day = new Day11();

      @Test
      void testPart1() {
          assertEquals("2243", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2027", day.part2().toString());
      }
  }
