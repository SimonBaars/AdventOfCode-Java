package com.sbaars.adventofcode.year20.days;

import static java.util.stream.IntStream.range;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.HashMap;
import java.util.Map;

public class Day15 extends Day2020 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
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
    range(0, nums.length - 1).forEach(i -> turnNumbers.put(nums[i], (long) i));
    long lastNumber = nums[nums.length - 1];
    for (long turnNumber = turnNumbers.size(); turnNumber < offset - 1; turnNumber++) {
      long newLastNumber = turnNumbers.containsKey(lastNumber) ? turnNumber - turnNumbers.get(lastNumber) : 0;
      turnNumbers.put(lastNumber, turnNumber);
      lastNumber = newLastNumber;
    }
    return lastNumber;
  }
}
