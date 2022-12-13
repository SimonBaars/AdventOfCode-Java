  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day3;

  class Day3Test {
      Day3 day = new Day3();

      @Test
      void testPart1() {
          assertEquals("7742", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("2276", day.part2().toString());
      }
  }
