package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends Day2015 {
  private final List<Long> packages;
  private final long totalWeight;

  public Day24() {
    super(24);
    packages = Arrays.stream(day().split("\n"))
        .mapToLong(Long::parseLong)
        .boxed()
        .collect(Collectors.toList());
    totalWeight = packages.stream().mapToLong(Long::longValue).sum();
  }

  public static void main(String[] args) {
    Day24 day = new Day24();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 24, 1);
  }

  @Override
  public Object part1() {
    return findOptimalConfiguration(3);
  }

  @Override
  public Object part2() {
    return findOptimalConfiguration(4);
  }

  private long findOptimalConfiguration(int numGroups) {
    long targetWeight = totalWeight / numGroups;
    int minSize = Integer.MAX_VALUE;
    long minQE = Long.MAX_VALUE;

    // Try all possible combinations for group 1
    for (int size = 1; size <= packages.size(); size++) {
      if (size >= minSize) break; // No need to check larger groups
      
      List<List<Long>> combinations = findCombinations(packages, size, targetWeight);
      if (!combinations.isEmpty()) {
        // Found valid combinations with this size
        minSize = size;
        // Find minimum quantum entanglement among these combinations
        for (List<Long> combo : combinations) {
          if (canSplitRemaining(packages, combo, targetWeight, numGroups - 1)) {
            long qe = calculateQE(combo);
            minQE = Math.min(minQE, qe);
          }
        }
        if (minQE != Long.MAX_VALUE) break; // Found valid configuration
      }
    }
    return minQE;
  }

  private List<List<Long>> findCombinations(List<Long> nums, int size, long target) {
    List<List<Long>> result = new ArrayList<>();
    findCombinationsHelper(nums, size, target, 0, new ArrayList<>(), result);
    return result;
  }

  private void findCombinationsHelper(List<Long> nums, int size, long target, int start,
      List<Long> current, List<List<Long>> result) {
    if (current.size() == size) {
      if (current.stream().mapToLong(Long::longValue).sum() == target) {
        result.add(new ArrayList<>(current));
      }
      return;
    }
    
    for (int i = start; i < nums.size(); i++) {
      current.add(nums.get(i));
      findCombinationsHelper(nums, size, target, i + 1, current, result);
      current.remove(current.size() - 1);
    }
  }

  private boolean canSplitRemaining(List<Long> allNums, List<Long> used, long targetWeight, int groups) {
    if (groups == 1) return true;
    
    List<Long> remaining = new ArrayList<>(allNums);
    remaining.removeAll(used);
    
    // Try different sizes for the next group
    for (int size = 1; size <= remaining.size(); size++) {
      List<List<Long>> combinations = findCombinations(remaining, size, targetWeight);
      for (List<Long> combo : combinations) {
        if (canSplitRemaining(remaining, combo, targetWeight, groups - 1)) {
          return true;
        }
      }
    }
    return false;
  }

  private long calculateQE(List<Long> nums) {
    return nums.stream().reduce(1L, (a, b) -> a * b);
  }
}
