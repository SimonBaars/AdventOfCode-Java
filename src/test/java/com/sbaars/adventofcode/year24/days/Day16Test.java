package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day16Test {
    Day16 day = new Day16();

    @Test
    void testPart1() {
        assertEquals("65436", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("489", day.part2().toString());
    }
}
