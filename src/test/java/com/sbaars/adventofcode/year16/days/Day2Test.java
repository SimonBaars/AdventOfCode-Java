package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("47978", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("659AD", day.part2().toString());
    }
}
