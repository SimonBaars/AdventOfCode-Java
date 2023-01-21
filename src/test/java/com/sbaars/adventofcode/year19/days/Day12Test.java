package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {
    Day12 day = new Day12();

    @Test
    void testPart1() {
        assertEquals("13399", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("312992287193064", day.part2().toString());
    }
}
