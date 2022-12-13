  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day21;

  class Day21Test {
      Day21 day = new Day21();

      @Test
      void testPart1() {
          assertEquals("19352864", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("1142488337", day.part2().toString());
      }
  }
