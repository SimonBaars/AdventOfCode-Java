package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.Arrays;
import java.util.stream.LongStream;

public class Day1 extends Day2022 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    Day d = new Day1();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
    return input().max().getAsLong();
  }

  @Override
  public Object part2() {
    long[] nums = input().sorted().toArray();
    return nums[nums.length-1] + nums[nums.length-2] + nums[nums.length-3];
  }

  private LongStream input () {
    return Arrays.stream(day().split("\n\n")).mapToLong(s -> Arrays.stream(s.split("\n")).map(String::trim).mapToLong(Long::parseLong).sum());
  }
}
