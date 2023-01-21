package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {
    Day18 day = new Day18();

    @Test
    void testPart1() {
        assertEquals("4207", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("4635", day.part2().toString());
    }
}
