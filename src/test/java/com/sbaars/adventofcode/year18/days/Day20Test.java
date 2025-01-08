package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day20Test {
    Day20 day = new Day20();

    @Test
    void testPart1() {
        assertEquals("3545", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("7838", day.part2().toString());
    }
}
