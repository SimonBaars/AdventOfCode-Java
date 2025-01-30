package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day12Test {
    Day12 day = new Day12();

    @Test
    void testPart1() {
        assertEquals("318020", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("9227674", day.part2().toString());
    }
}
