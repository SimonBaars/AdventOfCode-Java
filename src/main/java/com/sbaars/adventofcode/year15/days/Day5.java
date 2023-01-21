package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;

public class Day5 extends Day2015 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .filter(s -> s.replaceAll("a|e|i|o|u", "").length() <= s.length() - 3)
        .filter(s -> s.replaceAll("([a-z])\\1+", "").length() < s.length())
        .filter(s -> s.replaceAll("ab|cd|pq|xy", "").length() == s.length())
        .count();
  }

  @Override
  public Object part2() {
    return dayStream()
        .filter(s -> s.replaceAll("([a-z][a-z])(.*)\\1+", "").length() < s.length())
        .filter(s -> s.replaceAll("([a-z]).\\1", "").length() < s.length())
        .count();
  }
}
