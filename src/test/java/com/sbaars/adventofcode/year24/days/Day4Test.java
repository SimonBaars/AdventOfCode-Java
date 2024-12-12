package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day4Test {
    Day4 day = new Day4();

    @Test
    void testPart1() {
        assertEquals("2517", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1960", day.part2().toString());
    }
}
