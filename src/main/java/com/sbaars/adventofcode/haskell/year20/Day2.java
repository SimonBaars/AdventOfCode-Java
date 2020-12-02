package com.sbaars.adventofcode.haskell.year20;

import com.sbaars.adventofcode.year20.Day2020;

import java.io.IOException;
import java.util.stream.Collectors;

public class Day2 extends Day2020 {
    public Day2() {
        super(2);
    }

    public static void main(String[] args) throws IOException {
        new Day2().printParts();
    }

    @Override
    public Object part1() throws IOException {
        return dayStream().map(com.sbaars.adventofcode.year20.days.Day2::mapPassword)
                .map(p -> "(("+p.getLower()+", "+p.getHigher()+"), '"+p.getCharacter()+"', \""+p.getPassword()+"\")")
                .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public Object part2() throws IOException {
        return null;
    }
}
