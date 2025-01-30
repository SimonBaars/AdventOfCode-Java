package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day6Test {
    Day6 day = new Day6();

    @Test
    void testPart1() {
        assertEquals("3156", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1610", day.part2().toString());
    }
}
