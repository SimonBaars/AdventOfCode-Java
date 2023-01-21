package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day12 extends Day2022 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  @Override
  public Object part1() {
    NumGrid g = input();
    return findExit(g.find('S'), g);
  }

  public NumGrid input() {
    return new NumGrid(Arrays.stream(dayGrid()).map(e -> new String(e).chars().mapToLong(f -> f).toArray()).toArray(long[][]::new));
  }

  private long findExit(Point start, NumGrid g) {
    Set<Point> visited = new HashSet<>();
    Set<Point> currentLevel = new HashSet<>();
    currentLevel.add(start);
    visited.add(start);

    long steps = 1;
    while (!currentLevel.isEmpty()) {
      Set<Point> level = new HashSet<>(currentLevel);
      currentLevel.clear();
      for (Point p : level) {
        long current = g.get(p);
        if (current == 'S') current = 'a';
        for (Point p2 : g.streamFourDirs(p).toList()) {
          if ((current == 'y' || current == 'z') && g.get(p2) == 'E') return steps;
          if (g.get(p2) <= current + 1 && visited.add(p2)) {
            currentLevel.add(p2);
          }
        }
      }
      steps++;
    }
    return Long.MAX_VALUE;
  }

  @Override
  public Object part2() {
    NumGrid g = input();
    return g.findAll('a').filter(p -> p.y == 0).mapToLong(p -> findExit(p, g)).min().getAsLong();
  }
}
