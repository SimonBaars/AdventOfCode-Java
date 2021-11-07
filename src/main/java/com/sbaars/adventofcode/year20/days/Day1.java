package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;

public class Day1 extends Day2020 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    long[] nums = dayNumbers();
    return Arrays.stream(nums).flatMap(a ->
        Arrays.stream(nums).filter(b -> a + b == 2020L).map(b -> a * b)
    ).findAny().getAsLong();
  }

  @Override
  public Object part2() {
    long[] s = dayNumbers();
    for (long a : s) {
      for (long b : s) {
        for (long c : s) {
          if (a != b && b != c && a + b + c == 2020L) {
            return a * b * c;
          }
        }
      }
    }
    return 0;
  }
}
