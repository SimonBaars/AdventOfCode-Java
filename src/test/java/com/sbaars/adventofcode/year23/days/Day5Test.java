package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day5Test {
    Day5 day = new Day5();

    @Test
    void testPart1() {
        assertEquals("31599214", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("20358599", day.part2().toString());
    }
}
