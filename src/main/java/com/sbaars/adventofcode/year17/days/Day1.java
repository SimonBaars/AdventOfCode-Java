package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year17.Day2017;
import java.util.stream.IntStream;

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
    long[] digits = dayDigits();
    return IntStream.range(0, digits.length)
        .filter(i -> digits[i] == digits[(i + 1) % digits.length])
        .mapToLong(i -> digits[i])
        .sum();
  }

  @Override
  public Object part2() {
    long[] digits = dayDigits();
    return IntStream.range(0, digits.length)
        .filter(i -> digits[i] == digits[(i + digits.length / 2) % digits.length])
        .mapToLong(i -> digits[i])
        .sum();
  }
}
