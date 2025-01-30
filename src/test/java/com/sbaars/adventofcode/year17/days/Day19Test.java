package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("DTOUFARJQ", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("16642", day.part2().toString());
    }
}
