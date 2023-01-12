  package com.sbaars.adventofcode.year18.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year18.days.Day2;

  class Day2Test {
      Day2 day = new Day2();

      @Test
      void testPart1() {
          assertEquals("5456", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("megsdlpulxvinkatfoyzxcbvq", day.part2().toString());
      }
  }
