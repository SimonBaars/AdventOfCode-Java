  package com.sbaars.adventofcode.year21.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year21.days.Day4;

  class Day4Test {
      Day4 day = new Day4();

      @Test
      void testPart1() {
          assertEquals("45031", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2568", day.part2().toString());
      }
  }
