package com.sbaars.adventofcode.year20.days;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.IntStream.range;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 extends Day2020 {

    public static void main(String[] args) {
        new Day6().printParts();
    }

    public Day6() {
        super(6);
    }

    @Override
    public Object part1() {
        return Arrays.stream(day().split("\n\n"))
                .map(i -> i.replace("\n", ""))
                .mapToLong(i -> i.chars().distinct().count()).sum();
    }

    @Override
    public Object part2() {
        return Arrays.stream(day().split("\n\n")).mapToInt(group -> {
            String[] people = group.split("\n");
            List<Integer> c = new ArrayList<>(asList(people[0].chars().toArray()));
            range(1, people.length).forEach(i -> c.retainAll(asList(people[i].chars().toArray())));
            return c.size();
        }).sum();
    }
}
