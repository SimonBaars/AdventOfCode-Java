package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day21Test {
    Day21 day = new Day21();

    @Test
    void testPart1() {
        assertEquals("gbhcefad", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("gahedfcb", day.part2().toString());
    }
}
