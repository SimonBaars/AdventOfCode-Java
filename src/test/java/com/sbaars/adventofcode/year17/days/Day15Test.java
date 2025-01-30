package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day15Test {
    Day15 day = new Day15();

    @Test
    void testPart1() {
        assertEquals("619", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("290", day.part2().toString());
    }
}
