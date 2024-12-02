package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import java.util.stream.IntStream;

public class Day2 extends Day2024 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  @Override
  public Object part1() {
    return dayStream().filter(this::isSafeReport).count();
  }

  @Override
  public Object part2() {
    return dayStream().filter(this::isSafeWithDampener).count();
  }

  private boolean isSafeReport(String report) {
    String[] levels = report.split(" ");
    for (int i = 1; i < levels.length; i++) {
      int diff = Integer.parseInt(levels[i]) - Integer.parseInt(levels[i - 1]);
      if (Math.abs(diff) < 1 || Math.abs(diff) > 3 || (diff > 0 && i > 1 && Integer.parseInt(levels[i - 1]) < Integer.parseInt(levels[i - 2])) || (diff < 0 && i > 1 && Integer.parseInt(levels[i - 1]) > Integer.parseInt(levels[i - 2]))) {
        return false;
      }
    }
    return true;
  }

  private boolean isSafeWithDampener(String report) {
    if (isSafeReport(report)) {
      return true;
    }
    String[] levels = report.split(" ");
    return IntStream.range(0, levels.length)
        .anyMatch(i -> isSafeReport(removeLevel(levels, i)));
  }

  private String removeLevel(String[] levels, int index) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < levels.length; i++) {
      if (i != index) {
        sb.append(levels[i]).append(" ");
      }
    }
    return sb.toString().trim();
  }
}
