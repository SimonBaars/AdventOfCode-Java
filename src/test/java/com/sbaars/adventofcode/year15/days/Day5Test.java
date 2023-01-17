package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day5Test {
    Day5 day = new Day5();

    @Test
    void testPart1() {
        assertEquals("258", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("53", day.part2().toString());
    }
}
