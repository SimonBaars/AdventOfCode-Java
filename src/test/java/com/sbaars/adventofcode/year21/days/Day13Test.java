  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day13;

  class Day13Test {
      Day13 day = new Day13();

      @Test
      void testPart1() {
          assertEquals("647", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("HEJHJRCJ", day.part2().toString());
      }
  }
