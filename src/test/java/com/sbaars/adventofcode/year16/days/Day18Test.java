package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day18Test {
    Day18 day = new Day18();

    @Test
    void testPart1() {
        assertEquals("1963", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("20009568", day.part2().toString());
    }
}
