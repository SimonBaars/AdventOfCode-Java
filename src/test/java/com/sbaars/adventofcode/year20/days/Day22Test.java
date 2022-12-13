  package com.sbaars.adventofcode.year20.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year20.days.Day22;

  class Day22Test {
      Day22 day = new Day22();

      @Test
      void testPart1() {
          assertEquals("33925", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("33441", day.part2().toString());
      }
  }
