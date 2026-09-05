package com.sbaars.adventofcode.common;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayTest {

  /** Minimal Day stub so submitPart* can be exercised without network I/O. */
  static class StubDay extends Day {
    StubDay() {
      super(2015, 1);
    }

    @Override
    public Object part1() {
      return "part1-answer";
    }

    @Override
    public Object part2() {
      return "part2-answer";
    }
  }

  private static Object readSolutionField(Day day, String name) throws Exception {
    Field f = Day.class.getDeclaredField(name);
    f.setAccessible(true);
    return f.get(day);
  }

  @Test
  void submitPart2UsesPart2WhenSolutionNotCached() throws Exception {
    StubDay day = new StubDay();
    // Avoid calling Submit (network) while still allowing solution caching.
    day.setExample(1);
    day.submitPart2();
    assertEquals("part2-answer", readSolutionField(day, "solutionPart2"));
  }

  @Test
  void submitPart1UsesPart1WhenSolutionNotCached() throws Exception {
    StubDay day = new StubDay();
    day.setExample(1);
    day.submitPart1();
    assertEquals("part1-answer", readSolutionField(day, "solutionPart1"));
  }

  @Test
  void printPartsCachesDistinctPartAnswers() throws Exception {
    StubDay day = new StubDay();
    day.setExample(1);
    day.printParts();
    assertEquals("part1-answer", readSolutionField(day, "solutionPart1"));
    assertEquals("part2-answer", readSolutionField(day, "solutionPart2"));
  }
}
