package com.sbaars.adventofcode.year19.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    Day8 day = new Day8();

    @Test
    void testPart1() {
        assertEquals("1360", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("""
                                  
                ████ ███  █  █  ██  ███ \s
                █    █  █ █  █ █  █ █  █\s
                ███  █  █ █  █ █  █ █  █\s
                █    ███  █  █ ████ ███ \s
                █    █    █  █ █  █ █ █ \s
                █    █     ██  █  █ █  █\s""", day.part2().toString());
    }
}
