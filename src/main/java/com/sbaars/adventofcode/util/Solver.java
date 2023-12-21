package com.sbaars.adventofcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.AOCUtils.connectedPairs;
import static java.util.Collections.indexOfSubList;
import static java.util.stream.Collectors.toCollection;

/**
 * The solver looks for patterns in an infinite stream of numeric data, to find the result further in the repetition.
 */
public class Solver {
  // Anything below the MIN_PATTERN_LENGTH is a coincidence
  private static final int MIN_PATTERN_LENGTH = 10;

  public static <A> long solve(Stream<A> s, ToLongFunction<A> res, long target) {
    return solve(s, res, target, MIN_PATTERN_LENGTH);
  }

  public static <A> long solve(Stream<A> s, ToLongFunction<A> res, long target, int minSize) {
    List<Long> nums = new ArrayList<>();
    return s.map(n -> {
      nums.add(res.applyAsLong(n));
      return findPattern(nums, target, minSize);
    }).flatMap(Optional::stream).findFirst().get();
  }

  private static Optional<Long> findPattern(List<Long> nums, long target, int minSize) {
    if (nums.size() < minSize) {
      return Optional.empty();
    }
    return findDevelopingDeltaPattern(nums, target, minSize, 2);
//    return findCycleDeltaPattern(nums, target, minSize)
//        .or(() -> findDevelopingDeltaPattern(nums, target, minSize, 1));
  }

//  private static Optional<Long> findCycleDeltaPattern(List<Long> nums, long target, int minSize) {
//    List<Long> deltas = connectedPairs(nums).map(p -> p.b() - p.a()).collect(toCollection(ArrayList::new));
//    deltas.add(0, nums.get(0)); // baseline delta
//    target++; // Adjust target to cover baseline
//    if (deltas.size() >= minSize + 1 && deltas.subList(0, minSize).equals(deltas.subList(deltas.size() - minSize, deltas.size()))) {
//      long deltaPerRepetition = deltas.subList(0, deltas.size() - minSize).stream().mapToLong(e -> e).sum();
//      int elementsPerRepetition = deltas.size() - minSize;
//      long timesApplied = target / elementsPerRepetition;
//      int remainder = Math.toIntExact(target % elementsPerRepetition);
//      long applyRemainder = nums.subList(0, remainder).stream().mapToLong(e -> e).sum();
//      return Optional.of((deltaPerRepetition * timesApplied) + applyRemainder);
//    }
//    return Optional.empty();
//  }

  private static Optional<Long> findDevelopingDeltaPattern(List<Long> nums, long target, int minSize, int level) {
    List<Long> deltas = deltaAtLevel(nums, level);
    target++;
    if (deltas.size() >= minSize * 2) {
      System.out.println(deltas.get(deltas.size() - 1));
      int subListIndex = indexOfSubList(deltas, deltas.subList(deltas.size() - minSize, deltas.size()));
      if (subListIndex < deltas.size() - (minSize * 2) + 1) {
        System.out.println("Found pattern");
        List<Long> repeating = deltas.subList(subListIndex, deltas.size() - minSize);
        long deltaPerRepetition = repeating.stream().mapToLong(e -> e).sum();
        int elementsPerRepetition = repeating.size();
        long newTarget = target - subListIndex;
        long timesApplied = newTarget / elementsPerRepetition;
        int remainder = Math.toIntExact(newTarget % elementsPerRepetition);
        long applyRemainder = repeating.subList(0, remainder).stream().mapToLong(e -> e).sum();
        long beforePattern = deltas.subList(0, subListIndex).stream().mapToLong(e -> e).sum();
        return Optional.of((deltaPerRepetition * timesApplied) + applyRemainder + beforePattern);
      }
    }
    return Optional.empty();
  }

  private static List<Long> deltaAtLevel(List<Long> nums, int level) {
    List<Long> deltas = nums;
    for (int i = 0; i < level; i++) {
      deltas = connectedPairs(deltas).map(p -> p.b() - p.a()).collect(toCollection(ArrayList::new));
      if (i == 0) {
        deltas.add(0, nums.get(0));
      }
    }
    return deltas;
  }

}
