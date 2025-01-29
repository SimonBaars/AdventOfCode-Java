package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day2015 {

    private static final int TARGET_LITERS = 150;
    private final List<Integer> containers = new ArrayList<>();
    private final Map<Integer, Integer> containerCountMap = new HashMap<>();

    public Day17() {
        super(17);
        parseInput();
    }

    public static void main(String[] args) {
        Day17 day = new Day17();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 17, 1);
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 17, 2);
    }

    private void parseInput() {
        Arrays.stream(day().split("\n"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .forEach(containers::add);
    }

    @Override
    public Object part1() {
        return findCombinations(0, 0, new boolean[containers.size()], 0);
    }

    @Override
    public Object part2() {
        containerCountMap.clear();
        findCombinations(0, 0, new boolean[containers.size()], 0);
        int minContainers = containerCountMap.keySet().stream()
            .min(Integer::compareTo)
            .orElse(0);
        return containerCountMap.get(minContainers);
    }

    private int findCombinations(int index, int currentSum, boolean[] used, int containerCount) {
        if (currentSum == TARGET_LITERS) {
            containerCountMap.merge(containerCount, 1, Integer::sum);
            return 1;
        }
        if (currentSum > TARGET_LITERS || index >= containers.size()) {
            return 0;
        }

        // Don't use current container
        int withoutCurrent = findCombinations(index + 1, currentSum, used, containerCount);

        // Use current container
        used[index] = true;
        int withCurrent = findCombinations(index + 1, currentSum + containers.get(index), used, containerCount + 1);
        used[index] = false;

        return withoutCurrent + withCurrent;
    }
}
