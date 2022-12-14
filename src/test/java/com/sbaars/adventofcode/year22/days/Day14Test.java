  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day14;

  class Day14Test {
      Day14 day = new Day14();

      @Test
      void testPart1() {
          assertEquals("614", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("26170", day.part2().toString());
      }
  }
