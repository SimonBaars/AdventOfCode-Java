package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 extends Day2015 {
  private final Map<String, Map<String, Integer>> distances = new HashMap<>();
  private final Set<String> cities = new HashSet<>();

  public Day9() {
    super(9);
    parseInput();
  }

  public static void main(String[] args) {
    Day9 day = new Day9();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 9, 1);
    new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 9, 2);
  }

  private void parseInput() {
    day().lines().forEach(line -> {
      String[] parts = line.split(" = ");
      String[] cities = parts[0].split(" to ");
      int distance = Integer.parseInt(parts[1]);
      addDistance(cities[0], cities[1], distance);
      addDistance(cities[1], cities[0], distance);
      this.cities.add(cities[0]);
      this.cities.add(cities[1]);
    });
  }

  private void addDistance(String from, String to, int distance) {
    distances.computeIfAbsent(from, k -> new HashMap<>()).put(to, distance);
  }

  @Override
  public Object part1() {
    return findPath(true);
  }

  @Override
  public Object part2() {
    return findPath(false);
  }

  private int findPath(boolean shortest) {
    return cities.stream()
        .mapToInt(start -> findPathFromCity(start, new HashSet<>(List.of(start)), shortest))
        .reduce(shortest ? Integer.MAX_VALUE : Integer.MIN_VALUE, shortest ? Math::min : Math::max);
  }

  private int findPathFromCity(String current, Set<String> visited, boolean shortest) {
    if (visited.size() == cities.size()) {
      return 0;
    }

    return distances.get(current).entrySet().stream()
        .filter(e -> !visited.contains(e.getKey()))
        .mapToInt(e -> {
          Set<String> newVisited = new HashSet<>(visited);
          newVisited.add(e.getKey());
          return e.getValue() + findPathFromCity(e.getKey(), newVisited, shortest);
        })
        .reduce(shortest ? Integer.MAX_VALUE : Integer.MIN_VALUE, shortest ? Math::min : Math::max);
  }
}
