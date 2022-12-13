  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day14;

  class Day14Test {
      Day14 day = new Day14();

      @Test
      void testPart1() {
          assertEquals("485720", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("3848998", day.part2().toString());
      }
  }
