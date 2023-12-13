package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day13Test {
    Day13 day = new Day13();

    @Test
    void testPart1() {
        assertEquals("29213", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("37453", day.part2().toString());
    }
}
