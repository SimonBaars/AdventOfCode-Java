package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day18Test {
    Day18 day = new Day18();

    @Test
    void testPart1() {
        assertEquals("814", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("924", day.part2().toString());
    }
}
