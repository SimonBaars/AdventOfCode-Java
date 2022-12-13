  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day13;

  class Day13Test {
      Day13 day = new Day13();

      @Test
      void testPart1() {
          assertEquals("6369", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("25800", day.part2().toString());
      }
  }
