package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day12Test {
    Day12 day = new Day12();

    @Test
    void testPart1() {
        assertEquals("169", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("179", day.part2().toString());
    }
}
