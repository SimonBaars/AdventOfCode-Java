package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import static java.lang.Long.parseLong;

import java.util.List;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day7 extends Day2024 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    return calcTotalCalibRes(false);
  }

  @Override
  public Object part2() {
    return calcTotalCalibRes(true);
  }

  public record Line(long tgtVal, List<Long> nums) {}

  private long calcTotalCalibRes(boolean inclConcat) {
    return dayStream()
      .map(s -> readString(s, "%n: %ln", " ", Line.class))
      .filter(l -> canBeTrue(l.tgtVal, l.nums, 0, l.nums.get(0), inclConcat))
      .mapToLong(l -> l.tgtVal)
      .sum();
  }

  private boolean canBeTrue(long tgtVal, List<Long> nums, int i, long currVal, boolean inclConcat) {
    i++;
    if (i >= nums.size()) {
      return currVal == tgtVal;
    }

    long nextNum = nums.get(i);
    boolean res = canBeTrue(tgtVal, nums, i, currVal + nextNum, inclConcat) ||
                  canBeTrue(tgtVal, nums, i, currVal * nextNum, inclConcat);

    if (inclConcat) {
      long concatVal = parseLong(currVal + "" + nextNum);
      res = res || canBeTrue(tgtVal, nums, i, concatVal, inclConcat);
    }

    return res;
  }
}
