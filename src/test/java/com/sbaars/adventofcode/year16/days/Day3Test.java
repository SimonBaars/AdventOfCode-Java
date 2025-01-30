package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day3Test {
    Day3 day = new Day3();

    @Test
    void testPart1() {
        assertEquals("983", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1836", day.part2().toString());
    }
}
