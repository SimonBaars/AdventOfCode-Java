package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.stream.IntStream;

public class Day3 extends Day2022 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) throws IOException {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(e -> new String[]{e.substring(0, e.length() / 2), e.substring(e.length() / 2)})
        .mapToInt(e -> getPriorities(e[0]).filter(i -> getPriorities(e[1]).anyMatch(j -> j == i)).findFirst().getAsInt())
        .sum();
  }

  private IntStream getPriorities(String s) {
    return s.chars().map(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26);
  }

  @Override
  public Object part2() {
    String[] s = dayStream().map(String::trim).toArray(String[]::new);
    return IntStream.range(0, s.length / 3)
        .map(x -> x * 3)
        .map(x -> getPriorities(s[x]).filter(i -> getPriorities(s[x + 1]).anyMatch(j -> j == i) && getPriorities(s[x + 2]).anyMatch(j -> j == i)).findFirst().getAsInt())
        .sum();
  }
}
