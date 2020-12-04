package com.sbaars.adventofcode.haskell.year20;

import com.sbaars.adventofcode.year20.Day2020;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day3 extends Day2020 {
    public Day3() {
        super(3);
    }

    public static void main(String[] args)  {
        new Day3().printParts();
    }

    @Override
    public Object part1()  {
        return Arrays.stream(dayGrid()).map(Arrays::toString)
                .map(s -> s.replace('.', '0'))
                .map(s -> s.replace('#', '1'))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public Object part2()  {
        return null;
    }
}
