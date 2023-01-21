package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year17.Day2017;

public class Day1 extends Day2017 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    Day d = new Day1();
    d.downloadIfNotDownloaded();
    d.printParts();
  }

  @Override
  public Object part1() {
    long[] x = dayDigits();
    long sum = 0;
    for (int i = 0; i < x.length; i++) {
      if (x[i] == x[(i + 1) % x.length]) sum += x[i];
    }
    return sum;
  }

  @Override
  public Object part2() {
    long[] x = dayDigits();
    long sum = 0;
    for (int i = 0; i < x.length; i++) {
      if (x[i] == x[(i + x.length / 2) % x.length]) sum += x[i];
    }
    return sum;
  }
}
