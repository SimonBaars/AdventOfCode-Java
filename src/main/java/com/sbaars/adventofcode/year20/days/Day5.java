package com.sbaars.adventofcode.year20.days;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.Collections.sort;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day5 extends Day2020 {

    public static void main(String[] args)  {
        new Day5().printParts();
    }

    public Day5(){super(5);}

    @Override
    public Object part1()  {
        return getSeatIds(dayStrings()).stream().mapToInt(e -> e).max().getAsInt();
    }

    private Set<Integer> getSeatIds(String[] boardingPasses){
        Set<Integer> l = new HashSet<>();
        for(String s : boardingPasses){
            int rowLow = 0;
            int rowHigh = 127;
            int columnLow = 0;
            int columnHigh = 7;
            for(char c : s.toCharArray()){
                if(c == 'F'){
                    rowHigh -= (rowHigh-rowLow+1)/2;
                } else if(c == 'B'){
                    rowLow += (rowHigh-rowLow+1)/2;
                } else if(c == 'L'){
                    columnHigh -= (columnHigh-columnLow+1)/2;
                } else if(c == 'R'){
                    columnLow += (columnHigh-columnLow+1)/2;
                }
            }
            l.add(rowHigh * 8 + columnHigh);
        }
        return l;
    }

    @Override
    public Object part2()  {
        Set<Integer> l = getSeatIds(dayStrings());
        for(int n : l){
            if(l.contains(n) && l.contains(n+2) && !l.contains(n+1)){
                return n+1;
            }
        }
        return 0;
    }
}
