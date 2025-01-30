package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day5Test {
    Day5 day = new Day5();

    @Test
    void testPart1() {
        assertEquals("801b56a7", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("424a0197", day.part2().toString());
    }
}
