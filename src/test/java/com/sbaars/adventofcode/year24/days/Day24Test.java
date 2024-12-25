package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day24Test {
    Day24 day = new Day24();

    @Test
    void testPart1() {
        assertEquals("55544677167336", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("gsd,kth,qnf,tbt,vpm,z12,z26,z32", day.part2().toString());
    }
}
