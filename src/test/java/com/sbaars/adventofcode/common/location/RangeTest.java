package com.sbaars.adventofcode.common.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangeTest {
    @Test
    public void intersectsWith1(){
        Range range3 = new Range(0, 0, 4, 4);
        Range range4 = new Range(0, 4, 4, 0);
//        assertTrue(range1.intersectsWith(range2).isEmpty());
//        assertTrue(range2.intersectsWith(range1).isEmpty());
        assertEquals(new Loc(2, 2), range3.intersectsWith3(range4).get());
    }

    @Test
    public void intersectsWith2(){
        Range range5 = new Range(1, 0, 1, 4);
        Range range6 = new Range(0, 1, 4, 1);
        Range range7 = new Range(0, 2, 4, 1);
        Range range8 = new Range(0, 3, 4, 1);
        assertEquals(new Loc(1, 1), range5.intersectsWith3(range6).get());
    }

    @Test
    public void intersectsWith3(){
        Range range5 = new Range(1, 0, 1, 4);
        Range range6 = new Range(0, 1, 4, 1);
        Range range7 = new Range(0, 2, 4, 1);
        Range range8 = new Range(0, 3, 4, 1);
        assertEquals(new Loc(1, 2), range5.intersectsWith3(range7).get());
    }

    @Test
    public void intersectsWith4(){
        Range range5 = new Range(1, 0, 1, 4);
        Range range6 = new Range(0, 1, 4, 1);
        Range range7 = new Range(0, 2, 4, 1);
        Range range8 = new Range(0, 3, 4, 1);
        assertEquals(new Loc(1, 3), range5.intersectsWith2(range8).get());
    }
}
