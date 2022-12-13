  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day4;

  class Day4Test {
      Day4 day = new Day4();

      @Test
      void testPart1() {
          assertEquals("481", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("299", day.part2().toString());
      }
  }
