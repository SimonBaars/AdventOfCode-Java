package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day8Test {
    Day8 day = new Day8();

    @Test
    void testPart1() {
        assertEquals("6828", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("7234", day.part2().toString());
    }
}
