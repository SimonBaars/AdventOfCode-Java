package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.toIntExact;

public class Day25 extends Day2022 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) throws IOException {
    new Day25().printParts();
  }

  List<Character> digits = List.of('=', '-', '0', '1', '2');

  @Override
  public Object part1() {
    long res = dayStream().mapToLong(this::decimal).sum();
    StringBuilder out = new StringBuilder();
    while (res > 0) {
      int n = toIntExact((res + 2) % 5);
      res = (res + 2) / 5;
      out.insert(0, digits.get(n));
    }
    return out.toString();
  }

  private long decimal(String s) {
    long num = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(s.length() - 1 - i);
      long n = digits.indexOf(c) - 2L;
      long rad = (long) Math.pow(5, i);
      num += n * rad;
    }
    return num;
  }

  @Override
  public Object part2() {
    return "The More The Merrier";
  }
}
