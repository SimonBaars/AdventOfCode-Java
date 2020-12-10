package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;

public class Day10 extends HaskellDay2020 {
    public Day10() {
        super(10);
    }

    public static void main(String[] args)  {
        new Day10().printParts();
    }

    @Override
    public Object part1()  {
        return dayNumberStream().mapToObj(Long::toString).collect(haskellList());
    }

    @Override
    public Object part2()  {
        return null;
    }
}
