package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day24Test {
    Day24 day = new Day24();

    @Test
    void testPart1() {
        assertEquals("10723906903", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("74850409", day.part2().toString());
    }
}
