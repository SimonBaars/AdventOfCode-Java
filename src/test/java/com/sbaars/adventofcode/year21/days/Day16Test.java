  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day16;

  class Day16Test {
      Day16 day = new Day16();

      @Test
      void testPart1() {
          assertEquals("871", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("68703010504", day.part2().toString());
      }
  }
