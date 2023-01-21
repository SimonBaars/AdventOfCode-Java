package com.sbaars.adventofcode.year22.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {
    Day21 day = new Day21();

    @Test
    void testPart1() {
        assertEquals("83056452926300", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("3469704905529", day.part2().toString());
    }
}
