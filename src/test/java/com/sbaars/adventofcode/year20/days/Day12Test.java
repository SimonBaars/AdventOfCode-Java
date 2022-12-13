  package com.sbaars.adventofcode.year20.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year20.days.Day12;

  class Day12Test {
      Day12 day = new Day12();

      @Test
      void testPart1() {
          assertEquals("820", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("66614", day.part2().toString());
      }
  }
