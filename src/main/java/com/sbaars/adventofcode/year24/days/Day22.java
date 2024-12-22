package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.AoCUtils.applySelf;
import static com.sbaars.adventofcode.util.MuUtils.connectedPairs;
import static com.sbaars.adventofcode.util.AoCUtils.window;
import static com.sbaars.adventofcode.util.MuUtils.zipWithIndex;
import static java.util.stream.Collectors.toMap;
import java.util.*;

public class Day22 extends Day2024 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
      .filter(s -> !s.isEmpty())
      .mapToLong(Long::parseLong)
      .map(initSec -> applySelf(initSec, 2000, this::nextSec).toList().getLast())
      .sum();
  }

  private long nextSec(long sec) {
    long s1 = sec * 64;
    sec = sec ^ s1;
    sec = sec % 16777216;

    long s2 = sec / 32;
    sec = sec ^ s2;
    sec = sec % 16777216;

    long s3 = sec * 2048;
    sec = sec ^ s3;
    sec = sec % 16777216;

    return sec;
  }

  @Override
  public Object part2() {
    var map = dayStream()
      .map(String::trim)
      .filter(s -> !s.isEmpty())
      .map(Long::parseLong)
      .flatMap(initSec -> {
        var prices = applySelf(initSec, 2000, this::nextSec).map(sec -> (int)(sec % 10)).toList();
        var changes = connectedPairs(prices.stream()).mapToObj((a, b) -> b - a);
        var localSeqToBananas = new HashMap<List<Integer>, Long>();
        zipWithIndex(window(changes, 4)).filter((i, currSeq) -> !localSeqToBananas.containsKey(currSeq)).forEach((i, currSeq) -> {
          localSeqToBananas.put(currSeq, localSeqToBananas.getOrDefault(currSeq, 0L) + prices.get(i + 4));
        });
        return localSeqToBananas.entrySet().stream();
      })
      .collect(toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        Long::sum
      ));

    return map.values().stream().mapToLong(e -> e).max().getAsLong();
  }
}
