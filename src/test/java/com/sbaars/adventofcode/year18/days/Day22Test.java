package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day22Test {
    Day22 day = new Day22();

    @Test
    void testPart1() {
        assertEquals("8681", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1070", day.part2().toString());
    }
}
