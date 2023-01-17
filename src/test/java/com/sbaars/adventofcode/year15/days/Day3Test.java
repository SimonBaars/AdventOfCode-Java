package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day3Test {
    Day3 day = new Day3();

    @Test
    void testPart1() {
        assertEquals("2572", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("2631", day.part2().toString());
    }
}
