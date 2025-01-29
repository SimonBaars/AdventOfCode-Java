package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.IntStream;

public class Day20 extends Day2015 {

    private static final int TARGET_PRESENTS = 36000000;
    private static final int PRESENTS_PER_ELF = 10;

    public Day20() {
        super(20);
    }

    public static void main(String[] args) {
        Day20 day = new Day20();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 20, 2);
    }

    @Override
    public Object part1() {
        int houseNumber = 1;
        while (getPresentsForHouse(houseNumber) < TARGET_PRESENTS) {
            houseNumber++;
        }
        return houseNumber;
    }

    @Override
    public Object part2() {
        int houseNumber = 1;
        while (getPresentsForHousePart2(houseNumber) < TARGET_PRESENTS) {
            houseNumber++;
        }
        return houseNumber;
    }

    private int getPresentsForHousePart2(int houseNumber) {
        return getDivisors(houseNumber).stream()
            .filter(elf -> houseNumber / elf <= 50)  // Each Elf only visits 50 houses
            .mapToInt(elf -> elf * 11)  // Each Elf delivers 11 presents
            .sum();
    }

    private int getPresentsForHouse(int houseNumber) {
        return getDivisors(houseNumber).stream()
            .mapToInt(divisor -> divisor * PRESENTS_PER_ELF)
            .sum();
    }

    private Set<Integer> getDivisors(int number) {
        Set<Integer> divisors = new HashSet<>();
        for (int i = 1; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                divisors.add(i);
                divisors.add(number / i);
            }
        }
        return divisors;
    }
}
