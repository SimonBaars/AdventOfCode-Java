  package com.sbaars.adventofcode.year20.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year20.days.Day18;

  class Day18Test {
      Day18 day = new Day18();

      @Test
      void testPart1() {
          assertEquals("9535936849815", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("472171581333710", day.part2().toString());
      }
  }
