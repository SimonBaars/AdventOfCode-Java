package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day3Test {
    Day3 day = new Day3();

    @Test
    void testPart1() {
        assertEquals("184122457", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("107862689", day.part2().toString());
    }
}
