package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("47623", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("312", day.part2().toString());
    }
}
