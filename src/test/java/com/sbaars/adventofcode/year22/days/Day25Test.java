  package com.sbaars.adventofcode.year22.days;

  import org.junit.jupiter.api.Test;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  class Day25Test {
      Day25 day = new Day25();

      @Test
      void testPart1() {
          assertEquals("2=020-===0-1===2=020", day.part1().toString());
      }
  }
