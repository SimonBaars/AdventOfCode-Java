package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("1507611", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1880593125", day.part2().toString());
    }
}
