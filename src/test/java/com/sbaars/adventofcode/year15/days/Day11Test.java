package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day11Test {
    Day11 day = new Day11();

    @Test
    void testPart1() {
        assertEquals("hxbxxyzz", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("hxcaabcc", day.part2().toString());
    }
}
