package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;

public class Day2 extends Day2017 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(line -> Arrays.stream(line.split("\t"))
            .mapToInt(Integer::parseInt)
            .summaryStatistics())
        .mapToInt(stats -> stats.getMax() - stats.getMin())
        .sum();
  }

  @Override
  public Object part2() {
    return "";
  }
}
