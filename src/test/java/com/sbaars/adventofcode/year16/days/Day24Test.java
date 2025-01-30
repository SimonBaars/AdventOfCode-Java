package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day24Test {
    Day24 day = new Day24();

    @Test
    void testPart1() {
        assertEquals("442", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("660", day.part2().toString());
    }
}
