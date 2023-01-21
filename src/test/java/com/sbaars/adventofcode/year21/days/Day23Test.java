package com.sbaars.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Test {
    Day23 day = new Day23();

    @Test
    void testPart1() {
        assertEquals("13556", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("54200", day.part2().toString());
    }
}
