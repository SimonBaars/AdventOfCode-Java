package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day8Test {
    Day8 day = new Day8();

    @Test
    void testPart1() {
        assertEquals("17263", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("14631604759649", day.part2().toString());
    }
}
