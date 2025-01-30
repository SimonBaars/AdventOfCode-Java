package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day21Test {
    Day21 day = new Day21();

    @Test
    void testPart1() {
        assertEquals("78", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("148", day.part2().toString());
    }
}
