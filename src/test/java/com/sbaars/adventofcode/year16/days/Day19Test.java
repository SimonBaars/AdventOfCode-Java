package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("1841611", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1423634", day.part2().toString());
    }
}
