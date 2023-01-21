package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
  Day11 day = new Day11();

  @Test
  void testPart1() {
    assertEquals("2172", day.part1().toString());
  }

  @Test
  void testPart2() {
    assertEquals("""
                          
          ██ ████ █    ████ ████  ██  █  █ ███\s
           █ █    █    █    █    █  █ █  █ █  █
           █ ███  █    ███  ███  █    ████ █  █
           █ █    █    █    █    █ ██ █  █ ███\s
        █  █ █    █    █    █    █  █ █  █ █  \s
         ██  ████ ████ ████ █     ███ █  █ █  \s""", day.part2().toString());
  }
}
