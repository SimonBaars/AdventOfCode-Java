package com.sbaars.adventofcode.year20.days;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

import com.sbaars.adventofcode.year20.Day2020;

public class Day13 extends Day2020 {
    public static void main(String[] args) {
        new Day13().printParts();
    }

    public Day13() {
        super(13);
    }

    @Override
    public Object part1() {
        String[] day = dayStrings();
        int timestamp = parseInt(day[0]);
        int[] times = stream(day[1].replace(",x", "").split(","))
                .mapToInt(Integer::parseInt).toArray();
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
        String[] s = dayStrings()[1].split(",");
        long[][] nums = range(0, s.length).filter(i -> !s[i].equals("x"))
                .mapToObj(i -> new long[]{parseLong(s[i]), i})
                .toArray(long[][]::new);
        long product = stream(nums).mapToLong(a -> a[0]).reduce((a, b) -> a * b).getAsLong();
        long sum = stream(nums).mapToLong(a -> a[1] * (product/a[0]) * inverseModulo(product/a[0], a[0])).sum();
        return product - sum % product;
    }

    long inverseModulo(long x, long y){
        if(x!=0){
            long modulo = y % x;
            return modulo == 0 ? 1 : y - inverseModulo(modulo, x) * y / x;
        }
        return 0;
    }
}
