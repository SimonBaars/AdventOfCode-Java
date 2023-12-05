package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("1931", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("83105", day.part2().toString());
    }
}
