package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day5 extends HaskellDay2020 {
    public Day5() {
        super(5);
    }

    public static void main(String[] args)  {
        new Day5().printParts();
    }

    @Override
    public Object part1()  {
        return convert(dayStrings());
    }

    @Override
    public Object part2()  {
        return null;
    }
}
