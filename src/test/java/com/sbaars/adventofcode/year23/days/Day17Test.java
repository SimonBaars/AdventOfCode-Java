package com.sbaars.adventofcode.year23.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day17Test {
    Day17 day = new Day17();

    @Test
    void testPart1() {
        assertEquals("967", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1101", day.part2().toString());
    }
}
