package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("392421", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("2769449099", day.part2().toString());
    }
}
