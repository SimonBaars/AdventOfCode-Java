package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("7063", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("589", day.part2().toString());
    }
}
