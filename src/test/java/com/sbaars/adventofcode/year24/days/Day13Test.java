package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day13Test {
    Day13 day = new Day13();

    @Test
    void testPart1() {
        assertEquals("26299", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("107824497933339", day.part2().toString());
    }
}
