package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day1Test {
    Day1 day = new Day1();

    @Test
    void testPart1() {
        assertEquals("74", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1795", day.part2().toString());
    }
}
