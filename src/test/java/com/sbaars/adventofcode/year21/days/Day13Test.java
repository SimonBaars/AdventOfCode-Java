  package com.sbaars.adventofcode.year21.days;

  import org.junit.jupiter.api.Test;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  class Day13Test {
      Day13 day = new Day13();

      @Test
      void testPart1() {
          assertEquals("647", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("""
                  
                  1  1 1111   11 1  1   11 111   11    11
                  1  1 1       1 1  1    1 1  1 1  1    1
                  1111 111     1 1111    1 1  1 1       1
                  1  1 1       1 1  1    1 111  1       1
                  1  1 1    1  1 1  1 1  1 1 1  1  1 1  1
                  1  1 1111  11  1  1  11  1  1  11   11\s
                  """, day.part2().toString());
      }
  }
