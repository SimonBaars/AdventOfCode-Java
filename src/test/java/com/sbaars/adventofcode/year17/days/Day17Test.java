package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day17Test {
    Day17 day = new Day17();

    @Test
    void testPart1() {
        assertEquals("355", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("6154117", day.part2().toString());
    }
}
