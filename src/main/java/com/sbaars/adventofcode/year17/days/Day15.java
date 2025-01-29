package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day15 extends Day2017 {
    private static final long FACTOR_A = 16807;
    private static final long FACTOR_B = 48271;
    private static final long DIVISOR = 2147483647;
    private static final int MASK = 0xFFFF;  // For getting lowest 16 bits

    public Day15() {
        super(15);
    }

    public static void main(String[] args) {
        new Day15().printParts();
    }

    @Override
    public Object part1() {
        long genA = 722;  // puzzle input
        long genB = 354;  // puzzle input
        int matches = 0;

        for (int i = 0; i < 40_000_000; i++) {
            genA = (genA * FACTOR_A) % DIVISOR;
            genB = (genB * FACTOR_B) % DIVISOR;
            
            if ((genA & MASK) == (genB & MASK)) {
                matches++;
            }
        }

        return matches;
    }

    @Override
    public Object part2() {
        return 0;
    }
}
