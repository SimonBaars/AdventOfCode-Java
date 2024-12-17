package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day17Test {
    Day17 day = new Day17();

    @Test
    void testPart1() {
        assertEquals("2,0,4,2,7,0,1,0,3", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("265601188299675", day.part2().toString());
    }
}
