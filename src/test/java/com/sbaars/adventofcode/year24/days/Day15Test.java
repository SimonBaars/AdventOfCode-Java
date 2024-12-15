package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day15Test {
    Day15 day = new Day15();

    @Test
    void testPart1() {
        assertEquals("1517819", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1538862", day.part2().toString());
    }
}
