package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("535", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("212", day.part2().toString());
    }
}
