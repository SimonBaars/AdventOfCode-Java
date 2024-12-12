package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day7Test {
    Day7 day = new Day7();

    @Test
    void testPart1() {
        assertEquals("4122618559853", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("227615740238334", day.part2().toString());
    }
}
