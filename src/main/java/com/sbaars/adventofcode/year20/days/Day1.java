package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.Day;

import java.io.IOException;

public class Day1 implements Day {
    public static void main(String[] args) throws IOException {
        new Day1().printParts();
    }

    @Override
    public Object part1() throws IOException {
        long[] s = dayNumbers(1, "\n");
        for (long a : s) {
            for (long b : s) {
                if (a != b && a + b == 2020L) {
                    return a * b;
                }
            }
        }
        return 0;
    }

    @Override
    public Object part2() throws IOException {
        long[] s = dayNumbers(1, "\n");
        for (long a : s) {
            for (long b : s) {
                for (long c : s) {
                    if (a != b && b != c && a + b + c == 2020L) {
                        return a * b * c;
                    }
                }
            }
        }
        return 0;
    }
}
