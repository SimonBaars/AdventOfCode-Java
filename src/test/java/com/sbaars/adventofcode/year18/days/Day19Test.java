package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day19Test {
    Day19 day = new Day19();

    @Test
    void testPart1() {
        assertEquals("2160", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("25945920", day.part2().toString());
    }
}
