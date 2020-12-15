package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.util.*;

import static com.google.common.primitives.Longs.asList;
import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.lang.Long.parseLong;
import static java.lang.Long.toBinaryString;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

public class Day15 extends Day2020 {
    public static void main(String[] args) {
        new Day15().printParts();
    }

    public Day15() {
        super(15);
    }

    @Override
    public Object part1() {
        return getSolution(2020L);
    }

    @Override
    public Object part2() {
        return getSolution(30000000L);
    }

    private long getSolution(long offset) {
        Map<Long, Long> turnNumbers = new HashMap<>();
        long[] nums = dayNumbers(",");
        range(0, nums.length-1).forEach(i -> turnNumbers.put(nums[i], (long)i));
        long lastNumber = nums[nums.length-1];
        for(long turnNumber = turnNumbers.size(); turnNumber < offset - 1; turnNumber++){
            long newLastNumber = turnNumbers.containsKey(lastNumber) ? turnNumber - turnNumbers.get(lastNumber) : 0;
            turnNumbers.put(lastNumber, turnNumber);
            lastNumber = newLastNumber;
        }
        return lastNumber;
    }
}
