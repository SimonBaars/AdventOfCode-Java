  package com.sbaars.adventofcode.year20.days;

  import static org.junit.jupiter.api.Assertions.assertEquals;

  import org.junit.jupiter.api.Test;
  import com.sbaars.adventofcode.year20.days.Day21;

  class Day21Test {
      Day21 day = new Day21();

      @Test
      void testPart1() {
          assertEquals("2786", day.part1().toString());
      }

      @Test
      void testPart2() {
          assertEquals("prxmdlz,ncjv,knprxg,lxjtns,vzzz,clg,cxfz,qdfpq", day.part2().toString());
      }
  }
