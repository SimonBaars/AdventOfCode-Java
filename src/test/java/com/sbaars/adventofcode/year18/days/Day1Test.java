  package com.sbaars.adventofcode.year18.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year18.days.Day1;

  class Day1Test {
      Day1 day = new Day1();

      @Test
      void testPart1() {
          assertEquals("433", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("256", day.part2().toString());
      }
  }
