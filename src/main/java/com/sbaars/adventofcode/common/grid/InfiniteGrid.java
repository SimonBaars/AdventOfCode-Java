package com.sbaars.adventofcode.common.grid;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class InfiniteGrid implements Grid {
  final Map<Point, Character> grid = new HashMap<>();

  public InfiniteGrid(char[][] g) {
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        grid.put(new Point(j, i), g[i][j]);
      }
    }
  }

  public InfiniteGrid() {}

  public IntStream iterate() {
    return grid.values().stream().mapToInt(e -> e);
  }

  public Optional<Character> get(Point p) {
    return grid.containsKey(p) ? of(grid.get(p)) : empty();
  }

  public void set(Point p, char c) {
    grid.put(p, c);
  }

  public String toString() {
    int minX = grid.keySet().stream().mapToInt(e -> e.x).min().getAsInt();
    int minY = grid.keySet().stream().mapToInt(e -> e.y).min().getAsInt();
    int maxX = grid.keySet().stream().mapToInt(e -> e.x).max().getAsInt();
    int maxY = grid.keySet().stream().mapToInt(e -> e.y).max().getAsInt();
    CharGrid g = new CharGrid(' ', maxX+1-minX, maxY+1-minY);
    grid.forEach((p, i) -> g.set(new Point(p.x-minX, p.y-minY), i));
    return g.toString();
  }
}
