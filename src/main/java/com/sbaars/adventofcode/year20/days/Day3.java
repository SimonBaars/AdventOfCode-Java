package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class Day3 extends Day2020 implements ReadsFormattedString {
    public static void main(String[] args) throws IOException {
        new Day3().printParts();
    }

    public Day3(){super(3);}

    @Override
    public Object part1() throws IOException {
        String s = day();
        return 0;
    }

    @Override
    public Object part2() throws IOException {
        return 0;
    }
}
