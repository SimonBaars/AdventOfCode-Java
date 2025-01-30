package com.sbaars.adventofcode.year17.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("38628", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("e1462100a34221a7f0906da15c1c979a", day.part2().toString());
    }
}
