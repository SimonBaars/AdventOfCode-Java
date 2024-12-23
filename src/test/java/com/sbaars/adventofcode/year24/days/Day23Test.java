package com.sbaars.adventofcode.year24.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day23Test {
    Day23 day = new Day23();

    @Test
    void testPart1() {
        assertEquals("1110", day.part1().toString());
    }

    @Test
    void testPart2() {
        assertEquals("ej,hm,ks,ms,ns,rb,rq,sc,so,un,vb,vd,wd", day.part2().toString());
    }
}
