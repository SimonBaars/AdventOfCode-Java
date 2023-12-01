package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Map;

public class Day1 extends Day2023 {
  private static final Map<String, Integer> nums = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);

  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    System.out.println(day());
    return dayStream().map(s -> firstDigit(s).getRight() + lastDigit(s).getRight()).mapToInt(Integer::parseInt).sum();
  }

  private Pair<Integer, String> firstDigit(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (Character.isDigit(s.charAt(i))) {
        return Pair.of(i, s.charAt(i) + "");
      }
    }
    throw new IllegalStateException();
  }

  private Pair<Integer, String> lastDigit(String s) {
    for (int i = s.length() - 1; i >= 0; i--) {
      if (Character.isDigit(s.charAt(i))) {
        return Pair.of(i, s.charAt(i) + "");
      }
    }
    throw new IllegalStateException();
  }

  @Override
  public Object part2() {
    return dayStream().peek(System.out::println).map(s -> firstStringDigit(s) + lastStringDigit(s)).peek(System.out::println).mapToInt(Integer::parseInt).sum();
  }

  private String firstStringDigit(String s) {
    var pair = nums.keySet().stream().map(x -> Pair.of(x, s.indexOf(x))).filter(x -> x.getRight() != -1).sorted(Comparator.comparingInt(Pair::getRight)).findFirst().orElse(Pair.of("", Integer.MAX_VALUE));
    var firstDigit = firstDigit(s);
    if (firstDigit.getLeft() < pair.getRight()) {
      return firstDigit.getRight();
    } else {
      return nums.get(pair.getLeft()) + "";
    }
  }

  private String lastStringDigit(String s) {
    var pair = nums.keySet().stream().map(x -> Pair.of(x, s.lastIndexOf(x))).filter(x -> x.getRight() != -1).sorted((x, y) -> y.getRight() - x.getRight()).findFirst().orElse(Pair.of("", Integer.MIN_VALUE));
    var lastDigit = lastDigit(s);
    if (lastDigit.getLeft() > pair.getRight()) {
      return lastDigit.getRight();
    } else {
      return nums.get(pair.getLeft()) + "";
    }
  }
}
