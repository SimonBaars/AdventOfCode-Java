  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day18;

  class Day18Test {
      Day18 day = new Day18();

      @Test
      void testPart1() {
          assertEquals("3576", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2066", day.part2().toString());
      }
  }
