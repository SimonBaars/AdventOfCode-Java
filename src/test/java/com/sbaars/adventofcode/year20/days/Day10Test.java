package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("2812", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("386869246296064", day.part2().toString());
    }
}
