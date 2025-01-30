package com.sbaars.adventofcode.year15.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day2Test {
    Day2 day = new Day2();

    @Test
    void testPart1() {
        assertEquals("1586300", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("3737498", day.part2().toString());
    }
}
