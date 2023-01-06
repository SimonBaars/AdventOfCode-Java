  package com.sbaars.adventofcode.year19.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year19.days.Day25;

  class Day25Test {
      Day25 day = new Day25();

      @Test
      void testPart1() {
          assertEquals("34095120", day.part1().toString());
      }
  }
