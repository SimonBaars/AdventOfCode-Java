package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day16 extends Day2019 {

  private static final int TARGET_POS = 5977341;
  private final int[] input;

  public Day16() {
    super(16);
    input = day().chars().map(Character::getNumericValue).toArray();
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  public static int[] repeat(int[] arr, int newLength) {
    newLength = newLength * arr.length;
    int[] dup = Arrays.copyOf(arr, newLength);
    for (int last = arr.length; last != 0 && last < newLength; last <<= 1) {
      System.arraycopy(dup, 0, dup, last, Math.min(last << 1, newLength) - last);
    }
    return dup;
  }

  @Override
  public Object part1() {
    return calcRes(Arrays.copyOf(input, input.length), 0);
  }

  @Override
  public Object part2() {
    return calcRes(repeat(input, 10000), TARGET_POS);
  }

  private Object calcRes(int[] nums, final int offset) {
    int[] pattern = {0, 1, 0, -1};

    int[] res = new int[nums.length];
    for (int phase = 0; phase < 100; phase++) {
      int[] newNums = new int[nums.length + 1];
      for (int i = 0; i < nums.length; i++) {
        newNums[i + 1] = newNums[i] + nums[i];
      }
      for (int i = 0; i < nums.length; i++) {
        int sum = 0, loc = 0;
        for (int j = 0; true; j++) {

          int k = ((j + 1) * (i + 1)) - 1;
          sum += (newNums[Math.min(k, res.length)] - newNums[loc]) * pattern[j % 4];
          if (k >= res.length) break;
          loc = k;
        }
        res[i] = Math.abs(sum) % 10;
      }

      System.arraycopy(res, 0, nums, 0, res.length);
    }
    return Arrays.stream(res, offset, offset + 8).mapToObj(Integer::toString).collect(Collectors.joining());
  }

}
