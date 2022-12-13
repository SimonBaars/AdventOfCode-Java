  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day16;

  class Day16Test {
      Day16 day = new Day16();

      @Test
      void testPart1() {
          assertEquals("12541048", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("62858988", day.part2().toString());
      }
  }
