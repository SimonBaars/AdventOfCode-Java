package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day11Test {
    Day11 day = new Day11();

    @Test
    void testPart1() {
        assertEquals("172484", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("205913561055242", day.part2().toString());
    }
}
