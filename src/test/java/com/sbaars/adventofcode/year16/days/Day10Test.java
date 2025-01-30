package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("141", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1209", day.part2().toString());
    }
}
