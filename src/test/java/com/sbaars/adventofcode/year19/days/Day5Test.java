  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day5;

  class Day5Test {
      Day5 day = new Day5();

      @Test
      void testPart1() {
          assertEquals("11049715", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2140710", day.part2().toString());
      }
  }
