package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day20Test {
    Day20 day = new Day20();

    @Test
    void testPart1() {
        assertEquals("730797576", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("226732077152351", day.part2().toString());
    }
}
