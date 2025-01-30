package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day7Test {
    Day7 day = new Day7();

    @Test
    void testPart1() {
        assertEquals("vvsvez", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("362", day.part2().toString());
    }
}
