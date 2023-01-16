package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day7Test {
    Day7 day = new Day7();

    @Test
    void testPart1() {
        assertEquals("BFGKNRTWXIHPUMLQVZOYJACDSE", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("1163", day.part2().toString());
    }
}
