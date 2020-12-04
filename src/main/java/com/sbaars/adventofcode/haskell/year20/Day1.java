package com.sbaars.adventofcode.haskell.year20;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year20.Day2020;

import java.io.IOException;
import java.util.stream.Collectors;

public class Day1 extends Day2020 {
    public Day1() {
        super(1);
    }

    public static void main(String[] args)  {
        new Day1().printParts();
    }

    @Override
    public Object part1()  {
        return dayNumberStream().mapToObj(Long::toString).collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public Object part2()  {
        return null;
    }
}
