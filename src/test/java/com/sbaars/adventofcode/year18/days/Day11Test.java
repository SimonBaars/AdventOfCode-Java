package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day11Test {
    Day11 day = new Day11();

    @Test
    void testPart1() {
        assertEquals("235,16", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("236,227,14", day.part2().toString());
    }
}
