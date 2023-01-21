package com.sbaars.adventofcode.year20.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    Day8 day = new Day8();

    @Test
    void testPart1() {
        assertEquals("1420", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1245", day.part2().toString());
    }
}
