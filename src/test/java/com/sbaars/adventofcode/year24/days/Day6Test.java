package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day6Test {
    Day6 day = new Day6();

    @Test
    void testPart1() {
        assertEquals("4752", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1719", day.part2().toString());
    }
}
