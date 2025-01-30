package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day20Test {
    Day20 day = new Day20();

    @Test
    void testPart1() {
        assertEquals("831600", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("884520", day.part2().toString());
    }
}
