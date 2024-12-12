package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("306", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("366", day.part2().toString());
    }
}
