package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day23Test {
    Day23 day = new Day23();

    @Test
    void testPart1() {
        assertEquals("307", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("160", day.part2().toString());
    }
}
