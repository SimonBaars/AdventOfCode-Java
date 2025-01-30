package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day9Test {
    Day9 day = new Day9();

    @Test
    void testPart1() {
        assertEquals("141", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("736", day.part2().toString());
    }
}
