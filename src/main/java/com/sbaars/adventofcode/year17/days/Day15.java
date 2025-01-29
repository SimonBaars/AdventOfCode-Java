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

    private long nextValue(long prev, long factor) {
        return (prev * factor) % DIVISOR;
    }

    @Override
    public Object part1() {
        long genA = 591;  // puzzle input
        long genB = 393;  // puzzle input
        int matches = 0;

        for (int i = 0; i < 40_000_000; i++) {
            // Generate next values
            genA = nextValue(genA, FACTOR_A);
            genB = nextValue(genB, FACTOR_B);
            
            // Compare lowest 16 bits
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
