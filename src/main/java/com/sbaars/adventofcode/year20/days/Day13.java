package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year20.Day2020;
import java.awt.*;
import java.util.List;
import lombok.Data;
import lombok.Value;

public class Day13 extends Day2020 {
    public static void main(String[] args) {
        new Day13().printParts();
    }

    public Day13() {
        super(13);
    }

    @Override
    public Object part1() {
        int timestamp = 1005526;
        int[] times = {37,41,587,13,19,23,29,733,17};
        for(int i = timestamp; true; i++){
            for(int j : times){
                if(i%j == 0){
                    return j * (i-timestamp);
                }
            }
        }
    }

    @Override
    public Object part2() {
        int[] nums = {37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,41,0,0,0,0,0,0,0,0,0,587,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,19,0,0,0,23,0,0,0,0,0,29,0,733,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17};
        for(long match = 100000000000000L; true; match++){
            long finalMatch = match;
            if(range(0, nums.length).filter(j -> nums[j] != 0).allMatch(j -> (finalMatch + j) % nums[j] == 0)) {
                return match;
            }
        }
    }
}
