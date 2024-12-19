package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("374", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1100663950563322", day.part2().toString());
    }
}
