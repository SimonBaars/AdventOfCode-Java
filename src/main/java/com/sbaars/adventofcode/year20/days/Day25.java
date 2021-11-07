package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;

public class Day25 extends Day2020 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  @Override
  public Object part1() {
    long cardPublicKey = 1965712L;
    long doorPublicKey = 19072108L;
    long value = 1;
    for (int loopSize = 1; true; loopSize++) {
      value = transform(value, loopSize - 1, loopSize, 7);
      if (value == cardPublicKey) {
        return transform(1, 0, loopSize, doorPublicKey);
      }
    }
  }

  public long transform(long value, int start, int loopSize, long subjectNumber) {
    for (int i = start; i < loopSize; i++) {
      value *= subjectNumber;
      value %= 20201227;
    }
    return value;
  }

  @Override
  public Object part2() {
    return "That's all folks";
  }
}
