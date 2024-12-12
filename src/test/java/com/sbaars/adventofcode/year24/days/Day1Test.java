package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day1Test {
    Day1 day = new Day1();

    @Test
    void testPart1() {
        assertEquals("2285373", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("21142653", day.part2().toString());
    }
}
