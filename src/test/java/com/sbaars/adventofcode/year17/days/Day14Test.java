package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day14Test {
    Day14 day = new Day14();

    @Test
    void testPart1() {
        assertEquals("8214", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1093", day.part2().toString());
    }
}
