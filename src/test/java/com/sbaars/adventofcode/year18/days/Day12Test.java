package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day12Test {
    Day12 day = new Day12();

    @Test
    void testPart1() {
        assertEquals("4200", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("9699999999321", day.part2().toString());
    }
}
