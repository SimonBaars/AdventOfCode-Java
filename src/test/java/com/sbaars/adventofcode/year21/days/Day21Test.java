  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day21;

  class Day21Test {
      Day21 day = new Day21();

      @Test
      void testPart1() {
          assertEquals("678468", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("131180774190079", day.part2().toString());
      }
  }
