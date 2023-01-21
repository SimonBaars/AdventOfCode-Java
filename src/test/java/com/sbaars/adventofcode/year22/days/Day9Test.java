package com.sbaars.adventofcode.year22.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {
    Day9 day = new Day9();

    @Test
    void testPart1() {
        assertEquals("5735", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("2478", day.part2().toString());
    }
}
