  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day8;

  class Day8Test {
      Day8 day = new Day8();

      @Test
      void testPart1() {
          assertEquals("383", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("998900", day.part2().toString());
      }
  }
