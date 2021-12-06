package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year21.Day2021;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 extends Day2021 {

  public Day6() throws IOException {
    super(6);
  }

  public static void main(String[] args) throws IOException {
    new Day6().printParts();
    System.in.read();
//    new Day6().submitPart1();
    new Day6().submitPart2();
  }

  @Override
  public Object part1() {
    var in = Arrays.stream(day().replace("\n", "").split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toCollection(ArrayList::new));
    for(int j = 0; j<80; j++) {
      int size = in.size();
      for (int i = 0; i < size; i++) {
        if (in.get(i) == 0) in.add(8L);
        in.set(i, in.get(i) > 0 ? in.get(i) - 1 : 6);
      }
    }
    return in.size();
  }

  @Override
  public Object part2() {
    var in = Arrays.stream(day().replace("\n", "").split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toCollection(ArrayList::new));
    LongCountMap<Long> cm = new LongCountMap<>();
    for(var i : in) cm.increment(i);
    LongCountMap<Long> nc = new LongCountMap<>();
    for(int j = 0; j<256; j++) {
      for(var e : cm.entrySet()) {
        if(e.getKey() == 0){ nc.increment(8L, e.getValue()); nc.increment(6L, e.getValue()); }else {nc.increment(e.getKey()-1, e.getValue()); }
      }
      cm = nc;
      nc = new LongCountMap<>();
    }
    return cm.values().stream().mapToLong(e -> e).sum();
  }
}
