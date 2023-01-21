package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {
    Day17 day = new Day17();

    @Test
    void testPart1() {
        assertEquals("7404", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("929045", day.part2().toString());
    }
}
