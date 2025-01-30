package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day3Test {
    Day3 day = new Day3();

    @Test
    void testPart1() {
        assertEquals("326", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("363010", day.part2().toString());
    }
}
