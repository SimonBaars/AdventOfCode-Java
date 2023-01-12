package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day14 extends Day2021 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  @Override
  public Object part1() {
    return simulateSteps(10);
  }

  private long simulateSteps(int steps) {
    var in = day().split("\n\n");
    var init = in[0];
    var strs = Arrays.stream(in[1].split("\n")).map(e -> e.split(" -> ")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
    var pairs = new LongCountMap<String>();
    for(int i = 0; i<init.length()-1; i++) {
      pairs.increment(init.substring(i, i+2));
    }
    LongCountMap<Character> chCounts = new LongCountMap<>();
    init.chars().forEach(e -> chCounts.increment((char)e));
    for(int step = 1; step <= steps; step++){
      var newPairs = new LongCountMap<String>();
      for(String pair : pairs.keySet()){
        long increment = pairs.get(pair);
        if(strs.containsKey(pair)) {
          String key = strs.get(pair);
          String n1 = pair.substring(0, 1) + key;
          String n2 = key + pair.substring(1, 2);
          newPairs.increment(n1, increment);
          newPairs.increment(n2, increment);
          chCounts.increment(key.charAt(0), increment);
        } else {
          newPairs.increment(pair, increment);
        }
      }
      pairs = newPairs;
    }
    return chCounts.values().stream().mapToLong(e -> e).max().getAsLong() - chCounts.values().stream().mapToLong(e -> e).min().getAsLong();
  }

  @Override
  public Object part2() {
    return simulateSteps(40);
  }
}
