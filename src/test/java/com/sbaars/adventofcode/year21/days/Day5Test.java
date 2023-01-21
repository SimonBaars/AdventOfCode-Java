package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    Day5 day = new Day5();

    @Test
    void testPart1() {
        assertEquals("6283", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("18864", day.part2().toString());
    }
}
