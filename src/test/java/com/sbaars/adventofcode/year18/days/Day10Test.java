package com.sbaars.adventofcode.year18.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day10Test {
    Day10 day = new Day10();

    @Test
    void testPart1() {
        assertEquals(
              "\n ####   #####      ###  #####   #    #  #    #   ####   ######\n" +
                "#    #  #    #      #   #    #  ##   #  #   #   #    #  #     \n" +
                "#       #    #      #   #    #  ##   #  #  #    #       #     \n" +
                "#       #    #      #   #    #  # #  #  # #     #       #     \n" +
                "#       #####       #   #####   # #  #  ##      #       ##### \n" +
                "#       #           #   #  #    #  # #  ##      #       #     \n" +
                "#       #           #   #   #   #  # #  # #     #       #     \n" +
                "#       #       #   #   #   #   #   ##  #  #    #       #     \n" +
                "#    #  #       #   #   #    #  #   ##  #   #   #    #  #     \n" +
                " ####   #        ###    #    #  #    #  #    #   ####   #     \n" +
                "", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("10345", day.part2().toString());
    }
}
