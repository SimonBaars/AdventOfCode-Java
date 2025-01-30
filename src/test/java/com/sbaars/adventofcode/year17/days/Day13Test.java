package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day13Test {
    Day13 day = new Day13();

    @Test
    void testPart1() {
        assertEquals("1624", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("3923436", day.part2().toString());
    }
}
