  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day20;

  class Day20Test {
      Day20 day = new Day20();

      @Test
      void testPart1() {
          assertEquals("5498", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("3390007892081", day.part2().toString());
      }
  }
