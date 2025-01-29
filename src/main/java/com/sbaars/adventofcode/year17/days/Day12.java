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

  @Override
  public Object part1() {
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
    
    // Find all programs connected to 0 using BFS
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    queue.add(0);
    visited.add(0);
    
    while (!queue.isEmpty()) {
      int current = queue.poll();
      for (int neighbor : graph.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
    }
    
    return visited.size();
  }

  @Override
  public Object part2() {
    return "";
  }
}
