package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;

import java.util.stream.Collectors;

public class Day2 extends HaskellDay2020 {
    public Day2() {
        super(2);
    }

    public static void main(String[] args)  {
        new Day2().printParts();
    }

    @Override
    public Object part1()  {
        return dayStream().map(com.sbaars.adventofcode.year20.days.Day2::mapPassword)
                .map(p -> tuple(tup(p.getLower(), p.getHigher()), p.getCharacter(), convert(p.getPassword())))
                .collect(haskellList());
    }

    @Override
    public Object part2()  {
        return null;
    }
}
