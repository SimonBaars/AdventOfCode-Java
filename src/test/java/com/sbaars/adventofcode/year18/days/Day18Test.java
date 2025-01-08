package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day18Test {
    Day18 day = new Day18();

    @Test
    void testPart1() {
        assertEquals("384480", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("177004", day.part2().toString());
    }
}
