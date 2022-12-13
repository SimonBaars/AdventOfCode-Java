  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day22;

  class Day22Test {
      Day22 day = new Day22();

      @Test
      void testPart1() {
          assertEquals("7860", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("61256063148970", day.part2().toString());
      }
  }
