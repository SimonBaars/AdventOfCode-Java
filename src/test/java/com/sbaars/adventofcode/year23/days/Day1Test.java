package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day1Test {
    Day1 day = new Day1();

    @Test
    void testPart1() {
        assertEquals("54877", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("54100", day.part2().toString());
    }
}
