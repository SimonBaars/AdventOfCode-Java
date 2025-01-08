package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day24Test {
    Day24 day = new Day24();

    @Test
    void testPart1() {
        assertEquals("15470", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("5742", day.part2().toString());
    }
}
