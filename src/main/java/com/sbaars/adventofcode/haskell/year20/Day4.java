package com.sbaars.adventofcode.haskell.year20;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day4 extends Day2020 {
    public Day4() {
        super(4);
    }

    public static void main(String[] args)  {
        new Day4().printParts();
    }

    @Override
    public Object part1()  {
        String[][] passports = Arrays.stream(day().split("\n\n")).map(str -> str.replace("\n", " ")).map(str -> str.split(" ")).toArray(String[][]::new);
        return Arrays.stream(passports).map(passport ->
                Arrays.stream(passport).map(s -> s.split(":"))
                .map(s -> "(\""+s[0]+"\", \""+s[1]+"\")").collect(Collectors.joining(", ", "[", "]")))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public Object part2()  {
        return null;
    }
}
