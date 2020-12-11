package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;
import java.util.Arrays;

public class Day11 extends HaskellDay2020 {
    public Day11() {
        super(11);
    }

    public static void main(String[] args)  {
        new Day11().printParts();
    }

    @Override
    public Object part1()  {
        return Arrays.stream(dayGrid()).map(Arrays::toString)
                .map(s -> s.replace(".", "False"))
                .map(s -> s.replace("L", "True"))
                .collect(haskellList());
    }

    @Override
    public Object part2()  {
        return null;
    }
}
