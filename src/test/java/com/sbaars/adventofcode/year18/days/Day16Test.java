package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day16Test {
    Day16 day = new Day16();

    @Test
    void testPart1() {
        assertEquals("612", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("485", day.part2().toString());
    }
}
