package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import java.io.IOException;

public class Day4 extends Day2020 implements ReadsFormattedString {
    public static void main(String[] args) throws IOException {
        new Day4().printParts();
    }

    public Day4(){super(4);}

    @Override
    public Object part1() throws IOException {
        String s = day();
        return s;
    }

    @Override
    public Object part2() throws IOException {
        return 0;
    }
}
