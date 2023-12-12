package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Tuple;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.String.join;
import static java.util.Arrays.copyOf;

public class Day12 extends Day2023 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  public record Spring(String map, List<Integer> amounts) {
  }

  @Override
  public Object part1() {
    return input().mapToLong(spring ->
        countArrangements(new HashMap<>(), spring.map(), spring.amounts().stream().mapToInt(e -> e).toArray(), 0, 0, 0)
    ).sum();
  }

  @Override
  public Object part2() {
    return input().mapToLong(spring ->
        countArrangements(new HashMap<>(), join("?", spring.map(), spring.map(), spring.map(), spring.map(), spring.map()), repeat(spring.amounts().stream().mapToInt(e -> e).toArray(), 5), 0, 0, 0)
    ).sum();
  }

  public static int[] repeat(int[] arr, int times) {
    int newLength = arr.length * times;
    int[] dup = copyOf(arr, newLength);
    for (int last = arr.length; last != 0 && last < newLength; last <<= 1) {
      System.arraycopy(dup, 0, dup, last, Math.min(last << 1, newLength) - last);
    }
    return dup;
  }

  private Stream<Spring> input() {
    return dayStream().map(s -> readString(s, "%s %li", ",", Spring.class));
  }

  public long countArrangements(HashMap<Tuple<Integer, Integer, Integer>, Long> blockMap, String map, int[] amounts, int i, int j, int cur) {
    var key = new Tuple<>(i, j, cur);
    if (blockMap.containsKey(key)) {
      return blockMap.get(key);
    }
    if (i == map.length()) {
      return (j == amounts.length && cur == 0) || (j == amounts.length - 1 && amounts[j] == cur) ? 1 : 0;
    }
    long total = 0;
    char c = map.charAt(i);
    if ((c == '.' || c == '?') && cur == 0) {
      total += countArrangements(blockMap, map, amounts, i + 1, j, 0);
    } else if ((c == '.' || c == '?') && cur > 0 && j < amounts.length && amounts[j] == cur) {
      total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0);
    }
    if (c == '#' || c == '?') {
      total += countArrangements(blockMap, map, amounts, i + 1, j, cur + 1);
    }
    blockMap.put(key, total);
    return total;
  }
}