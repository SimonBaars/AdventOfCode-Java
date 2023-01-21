package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("104", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("314", day.part2().toString());
    }
}
