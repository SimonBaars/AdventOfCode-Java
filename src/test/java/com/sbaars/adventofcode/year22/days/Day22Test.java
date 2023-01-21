package com.sbaars.adventofcode.year22.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test {
    Day22 day = new Day22();

    @Test
    void testPart1() {
        assertEquals("58248", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("179091", day.part2().toString());
    }
}
