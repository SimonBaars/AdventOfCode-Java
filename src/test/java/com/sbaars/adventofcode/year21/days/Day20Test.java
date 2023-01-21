package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {
    Day20 day = new Day20();

    @Test
    void testPart1() {
        assertEquals("5218", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("15527", day.part2().toString());
    }
}
