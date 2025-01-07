package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day25Test {
    Day25 day = new Day25();

    @Test
    void testPart1() {
        assertEquals("600369", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("true", day.part2().toString());
    }
}
