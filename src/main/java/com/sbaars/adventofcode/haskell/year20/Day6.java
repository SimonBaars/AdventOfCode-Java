package com.sbaars.adventofcode.haskell.year20;

import static java.util.stream.Collectors.joining;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 extends Day2020 {
    public Day6() {
        super(6);
    }

    public static void main(String[] args)  {
        new Day6().printParts();
    }

    @Override
    public Object part1()  {
        return Arrays.stream(day().split("\n\n"))
                .map(i -> Arrays.stream(i.split("\n"))
                        .map(s -> "\""+s+"\"").collect(joining(", ", "[", "]")))
                .collect(joining(", ", "[", "]"));
    }

    @Override
    public Object part2()  {
        return null;
    }
}
