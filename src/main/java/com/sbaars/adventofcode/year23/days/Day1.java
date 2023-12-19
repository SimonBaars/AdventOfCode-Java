package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

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
    return dayStream().map(s -> firstDigit(s).getRight() + lastDigit(s).getRight()).mapToInt(Integer::parseInt).sum();
  }

  private Pair<Integer, String> firstDigit(String s) {
    return pairStream(s).findFirst().get();
  }

  private Pair<Integer, String> lastDigit(String s) {
    return pairStream(s).reduce((x, y) -> y).get();
  }

  private static Stream<Pair<Integer, String>> pairStream(String s) {
    return range(0, s.length()).mapToObj(i -> Pair.of(i, s.charAt(i) + "")).filter(x -> Character.isDigit(x.getRight().charAt(0)));
  }

  @Override
  public Object part2() {
    return dayStream().map(s -> firstStringDigit(s) + lastStringDigit(s)).mapToInt(Integer::parseInt).sum();
  }

  private String firstStringDigit(String s) {
    var pair = nums.keySet().stream().map(x -> Pair.of(x, s.indexOf(x))).filter(x -> x.getRight() != -1).min(Comparator.comparingInt(Pair::getRight)).orElse(Pair.of("", Integer.MAX_VALUE));
    var firstDigit = firstDigit(s);
    if (firstDigit.getLeft() < pair.getRight()) {
      return firstDigit.getRight();
    }
    return nums.get(pair.getLeft()) + "";
  }

  private String lastStringDigit(String s) {
    var pair = nums.keySet().stream().map(x -> Pair.of(x, s.lastIndexOf(x))).filter(x -> x.getRight() != -1).min((x, y) -> y.getRight() - x.getRight()).orElse(Pair.of("", Integer.MIN_VALUE));
    var lastDigit = lastDigit(s);
    if (lastDigit.getLeft() > pair.getRight()) {
      return lastDigit.getRight();
    }
    return nums.get(pair.getLeft()) + "";
  }
}
