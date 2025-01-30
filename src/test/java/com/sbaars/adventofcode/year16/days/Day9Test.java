package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day9Test {
    Day9 day = new Day9();

    @Test
    void testPart1() {
        assertEquals("70186", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("10915059201", day.part2().toString());
    }
}
