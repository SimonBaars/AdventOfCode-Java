package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;
import java.util.Set;

public class Day1 extends Day2018 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    return dayNumberStream().sum();
  }

  @Override
  public Object part2() {
    Set<Long> encountered = new HashSet<>();
    long[] ns = dayNumbers();
    long acc = 0;
    while (true) {
      for (long n : ns) {
        acc += n;
        if (!encountered.add(acc)) return acc;
      }
    }
  }
}
