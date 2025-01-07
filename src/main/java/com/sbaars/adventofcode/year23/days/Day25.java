package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;
import java.util.*;
import java.util.stream.IntStream;

public class Day25 extends Day2023 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  private static class Graph {
    Map<String, Set<String>> adj = new HashMap<>();

    void addEdge(String u, String v) {
      adj.computeIfAbsent(u, k -> new HashSet<>()).add(v);
      adj.computeIfAbsent(v, k -> new HashSet<>()).add(u);
    }

    int[] findMinCut() {
      Set<String> vertices = adj.keySet();
      Set<String> group = new HashSet<>();
      Set<String> remaining = new HashSet<>(vertices);
      
      String start = vertices.iterator().next();
      group.add(start);
      remaining.remove(start);
      
      Map<String, Long> connections = adj.get(start).stream()
          .collect(HashMap::new, (m, v) -> m.put(v, 1L), Map::putAll);
      
      while (!remaining.isEmpty()) {
        String maxVertex = remaining.stream()
            .max(Comparator.comparingLong(v -> connections.getOrDefault(v, 0L)))
            .orElseThrow();
        
        group.add(maxVertex);
        remaining.remove(maxVertex);
        adj.get(maxVertex).stream()
            .filter(remaining::contains)
            .forEach(v -> connections.merge(v, 1L, Long::sum));
        
        long cutSize = group.stream()
            .flatMap(v -> adj.get(v).stream())
            .filter(v -> !group.contains(v))
            .count();
            
        if (cutSize == 3) {
          return new int[]{3, group.size(), vertices.size() - group.size()};
        }
      }
      
      return new int[]{-1, 0, 0};
    }
  }

  private Graph parseInput(String input) {
    Graph g = new Graph();
    Arrays.stream(input.split("\n"))
        .map(line -> line.split(": "))
        .forEach(parts -> Arrays.stream(parts[1].split(" "))
            .forEach(target -> g.addEdge(parts[0], target)));
    return g;
  }

  @Override
  public Object part1() {
    Graph g = parseInput(day());
    return IntStream.range(0, 100)
        .mapToObj(i -> g.findMinCut())
        .filter(result -> result[0] == 3)
        .findFirst()
        .map(result -> (Object)(result[1] * result[2]))
        .get();
  }

  @Override
  public Object part2() {
    return "That's all!";
  }
}
