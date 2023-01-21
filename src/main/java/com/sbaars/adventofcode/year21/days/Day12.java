package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.common.map.CountMap;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.map.ListMap.toListMap;

public class Day12 extends Day2021 implements HasRecursion {

  public static final String START = "start";

  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  @Override
  public Object part1() {
    return findPos(START, getInput(), Set.of(START));
  }

  private ListMap<String, String> getInput() {
    return dayStream().map(e -> e.split("-")).flatMap(e -> Stream.of(new String[]{e[0], e[1]}, new String[]{e[1], e[0]})).collect(toListMap(e -> e[0], e -> e[1]));
  }

  private long findPos(String s, ListMap<String, String> in, Set<String> visited) {
    if (s.equals("end")) return 1;
    long n = 0;
    List<String> reachable = in.get(s);
    for (String l : reachable) {
      if (!visited.contains(l)) {
        Set<String> newSet = new HashSet<>(visited);
        if (!l.toUpperCase().equals(l)) newSet.add(l);
        n += findPos(l, in, newSet);
      }
    }
    return n;
  }

  @Override
  public Object part2() {
    return findPos2(START, getInput(), Map.of(START, 2));
  }

  private long findPos2(String s, ListMap<String, String> in, Map<String, Integer> visited) {
    if (s.equals("end")) {
      return 1;
    }
    long n = 0;
    List<String> reachable = in.get(s);
    for (String l : reachable) {
      if ((!visited.containsKey(l) || visited.get(l) < 2) && visited.values().stream().filter(e -> e == 2).count() <= 2) {
        CountMap<String> newSet = new CountMap<>(visited);
        if (!l.toUpperCase().equals(l)) newSet.increment(l);
        n += findPos2(l, in, newSet);
      }
    }
    return n;
  }
}
