package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Pair.pair;

import java.util.HashMap;
import java.util.Map;

public class Day11 extends Day2024 {
  private final Map<Pair<Long, Integer>, Long> memo = new HashMap<>();

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    return calculateStones(25);
  }

  @Override
  public Object part2() {
    return calculateStones(75);
  }

  private long calculateStones(int totalBlinks) {
    return dayStream(" ").map(String::trim).mapToLong(Long::parseLong).map(stone -> countStones(stone, totalBlinks)).sum();
  }

  private long countStones(long stone, int blinksLeft) {
    if (blinksLeft == 0) {
      return 1;
    }
    var key = pair(stone, blinksLeft);
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    memo.put(key, amount(stone, blinksLeft));
    return memo.get(key);
  }

  private long amount(long s, int blinksLeft) {
    if (s != 0) {
      long n = s == 0 ? 1 : (long) Math.log10(Math.abs(s)) + 1;
      if (n % 2 == 0) {
        long pow = (long) Math.pow(10, n / 2);
        return countStones(s / pow, blinksLeft - 1) + countStones(s % pow, blinksLeft - 1);
      }
      return countStones(s * 2024, blinksLeft - 1);
    }
    return countStones(1L, blinksLeft - 1);
  }
}
