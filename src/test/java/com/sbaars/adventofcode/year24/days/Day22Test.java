package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day22Test {
    Day22 day = new Day22();

    @Test
    void testPart1() {
        assertEquals("13764677935", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1619", day.part2().toString());
    }
}
