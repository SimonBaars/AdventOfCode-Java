package com.sbaars.adventofcode.year19.days;

import com.google.common.collect.ArrayListMultimap;
import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.year19.Day2019;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day6 extends Day2019 implements HasRecursion {
  ArrayListMultimap<String, String> orbits = ArrayListMultimap.create();

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    String[] nums = dayStrings();
    for (String num : nums) {
      String[] parts = num.split("\\)");
      orbits.put(parts[0], parts[1]);
    }
    AtomicInteger o = new AtomicInteger();
    for (Entry<String, Collection<String>> entry : orbits.asMap().entrySet()) {
      countOrbitsInList(orbits, o, entry.getValue());
    }
    return o.get();
  }

  private void countOrbitsInList(ArrayListMultimap<String, String> orbits, AtomicInteger o, Collection<String> entry) {
    for (String str : entry) {
      o.incrementAndGet();
      if (orbits.containsKey(str)) {
        countOrbitsInList(orbits, o, orbits.get(str));
      }
    }
  }

  @Override
  public Object part2() {
    return findRoute("YOU", "SAN");
  }

  private int findRoute(String from, String to) {
    return findRoute(from, to, new ArrayList<>(), 0);
  }

  private int findRoute(String from, String to, List<String> visited, int depth) {
    if (visited.contains(from))
      return 0;
    visited.add(from);
    List<String> str = collectAll(from);
    if (str.contains(to))
      return depth - 1;
    for (String s : str) {
      int findRoute = findRoute(s, to, visited, depth + 1);
      if (findRoute > 0) return findRoute;
    }
    return -1;
  }

  private List<String> collectAll(String s1) {
    List<String> s = findOrbit(s1);
    s.addAll(orbits.get(s1));
    return s;
  }

  public List<String> findOrbit(String orbitValue) {
    return orbits.asMap().entrySet().stream().filter(e -> e.getValue().contains(orbitValue)).map(Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
  }
}
