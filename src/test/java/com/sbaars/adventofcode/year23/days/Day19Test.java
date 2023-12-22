package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("352052", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("116606738659695", day.part2().toString());
    }
}
