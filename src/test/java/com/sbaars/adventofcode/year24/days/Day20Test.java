package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day20Test {
    Day20 day = new Day20();

    @Test
    void testPart1() {
        assertEquals("1289", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("982425", day.part2().toString());
    }
}
