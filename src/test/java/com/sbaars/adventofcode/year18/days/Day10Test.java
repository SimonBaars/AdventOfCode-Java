package com.sbaars.adventofcode.year18.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals("""
                
                 ####   #####      ###  #####   #    #  #    #   ####   ######
                #    #  #    #      #   #    #  ##   #  #   #   #    #  #    \s
                #       #    #      #   #    #  ##   #  #  #    #       #    \s
                #       #    #      #   #    #  # #  #  # #     #       #    \s
                #       #####       #   #####   # #  #  ##      #       #####\s
                #       #           #   #  #    #  # #  ##      #       #    \s
                #       #           #   #   #   #  # #  # #     #       #    \s
                #       #       #   #   #   #   #   ##  #  #    #       #    \s
                #    #  #       #   #   #    #  #   ##  #   #   #    #  #    \s
                 ####   #        ###    #    #  #    #  #    #   ####   #    \s
                """, day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("10345", day.part2().toString());
    }
}
