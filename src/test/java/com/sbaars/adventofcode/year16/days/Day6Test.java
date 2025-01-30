package com.sbaars.adventofcode.year16.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day6Test {
    Day6 day = new Day6();

    @Test
    void testPart1() {
        assertEquals("qrqlznrl", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("kgzdfaon", day.part2().toString());
    }
}
