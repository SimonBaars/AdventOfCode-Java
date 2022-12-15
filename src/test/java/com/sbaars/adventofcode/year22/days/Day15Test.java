  package com.sbaars.adventofcode.year22.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year22.days.Day15;

  class Day15Test {
      Day15 day = new Day15();

      @Test
      void testPart1() {
          assertEquals("4961647", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("12274327017867", day.part2().toString());
      }
  }
