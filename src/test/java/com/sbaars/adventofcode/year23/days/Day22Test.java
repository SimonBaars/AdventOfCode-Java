package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day22Test {
    Day22 day = new Day22();

    @Test
    void testPart1() {
        assertEquals("443", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("69915", day.part2().toString());
    }
}
