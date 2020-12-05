package com.sbaars.adventofcode.haskell.year20;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day5 extends Day2020 {
    public Day5() {
        super(5);
    }

    public static void main(String[] args)  {
        new Day5().printParts();
    }

    @Override
    public Object part1()  {
        return Arrays.stream(dayStrings()).map(s -> "\""+s+"\"").collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public Object part2()  {
        return null;
    }
}
