package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Day2017 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }
  
  private Map<Integer, Set<Integer>> buildGraph() {
    Map<Integer, Set<Integer>> graph = new HashMap<>();
    Pattern pattern = Pattern.compile("(\\d+) <-> (.+)");
    
    // Build the graph
    for (String line : dayStrings()) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        int program = Integer.parseInt(m.group(1));
        Set<Integer> connections = new HashSet<>();
        for (String conn : m.group(2).split(", ")) {
          connections.add(Integer.parseInt(conn));
        }
        graph.put(program, connections);
      }
    }
    
    return graph;
  }
  
  private Set<Integer> findGroup(Map<Integer, Set<Integer>> graph, int start, Set<Integer> visited) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> group = new HashSet<>();
    queue.add(start);
    group.add(start);
    
    while (!queue.isEmpty()) {
      int current = queue.poll();
      for (int neighbor : graph.get(current)) {
        if (!group.contains(neighbor)) {
          group.add(neighbor);
          queue.add(neighbor);
        }
      }
    }
    
    visited.addAll(group);
    return group;
  }

  @Override
  public Object part1() {
    Map<Integer, Set<Integer>> graph = buildGraph();
    return findGroup(graph, 0, new HashSet<>()).size();
  }

  @Override
  public Object part2() {
    Map<Integer, Set<Integer>> graph = buildGraph();
    Set<Integer> visited = new HashSet<>();
    int groups = 0;
    
    for (int program : graph.keySet()) {
      if (!visited.contains(program)) {
        findGroup(graph, program, visited);
        groups++;
      }
    }
    
    return groups;
  }
}
