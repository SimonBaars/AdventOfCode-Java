  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day18;

  class Day18Test {
      Day18 day = new Day18();

      @Test
      void testPart1() {
          assertEquals("5402", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2138", day.part2().toString());
      }
  }
