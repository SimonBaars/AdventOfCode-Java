package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.Grid;
import com.sbaars.adventofcode.year19.Day2019;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day24 extends Day2019 {

  private final Set<Grid> grids = new HashSet<>();
  private final char[][] initialGrid;
  private Map<Integer, char[][]> layers = new HashMap<>();

  public Day24() {
    super(24);
    this.initialGrid = dayGrid();
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  @Override
  public Object part1() {
    char[][] grid = initialGrid;
    while (true) {
      if (!grids.add(new CharGrid(grid))) return calcRes(grid);
      char[][] newGrid = copy(grid);
      final char[][] g = grid;
      streamGrid(grid).forEach(p -> simulate(g, newGrid, p));
      grid = newGrid;
    }
  }

  private void simulate(char[][] grid, char[][] newGrid, Point p) {
    int adj = nAdjecent(grid, p);
    if (get(grid, p) == '#' && adj != 1) {
      set(newGrid, p, '.');
    } else if (get(grid, p) == '.' && (adj == 1 || adj == 2)) {
      set(newGrid, p, '#');
    }
  }

  private long calcRes(char[][] grid) {
    return (long) streamGrid(grid).filter(p -> get(grid, p) == '#').mapToDouble(p -> Math.pow(2, (p.y * grid.length) + p.x)).sum();
  }

  public char[][] copy(char[][] grid) {
    char[][] g2 = new char[grid.length][];
    for (int i = 0; i < grid.length; i++) g2[i] = Arrays.copyOf(grid[i], grid[i].length);
    return g2;
  }

  public int nAdjecent(char[][] grid, Point pos) {
    return num(grid, new Point(pos.x, pos.y - 1)) + num(grid, new Point(pos.x, pos.y + 1)) + num(grid, new Point(pos.x - 1, pos.y)) + num(grid, new Point(pos.x + 1, pos.y));
  }

  private int num(char[][] grid, Point p) {
    if (p.x < 0 || p.y < 0 || p.x >= grid.length || p.y >= grid.length) return 0;
    return get(grid, p) == '#' ? 1 : 0;
  }

  @Override
  public Object part2() {
    char[][] grid = dayGrid();
    for (int i = -200; i <= 200; i++) layers.put(i, fill(new char[grid.length][grid[0].length], '.'));
    layers.put(0, grid);
    for (int i = 0; i < 200; i++) {
      final Map<Integer, char[][]> layers2 = new HashMap<>();
      for (int layer = -200; layer <= 200; layer++) {
        char[][] newGrid = copy(layers.get(layer));
        final int l = layer;
        streamGrid(grid).filter(p -> p.x != 2 || p.y != 2).forEach(p -> simulate(l, newGrid, p));
        layers2.put(layer, newGrid);
      }
      layers = layers2;
    }
    return layers.values().stream().mapToLong(e -> count(e, '#')).sum();
  }

  public Stream<Point> streamGrid(int sizex, int sizey) {
    return IntStream.range(0, sizey).boxed().flatMap(y -> IntStream.range(0, sizex).mapToObj(x -> new Point(x, y)));
  }

  public Stream<Point> streamGrid(char[][] grid) {
    return streamGrid(grid[0].length, grid.length);
  }

  private long count(char[][] grid, char c) {
    return streamGrid(grid).filter(p -> get(grid, p) == '#').count();
  }

  private void simulate(int layer, char[][] newGrid, Point p) {
    int adj = nAdjecent(layer, p);
    if (get(layers.get(layer), p) == '#' && adj != 1) {
      set(newGrid, p, '.');
    } else if (get(layers.get(layer), p) == '.' && (adj == 1 || adj == 2)) {
      set(newGrid, p, '#');
    }
  }

  private char get(char[][] grid, Point pos) {
    return grid[pos.y][pos.x];
  }

  private void set(char[][] grid, Point pos, char c) {
    grid[pos.y][pos.x] = c;
  }

  private int nAdjecent(int layer, Point pos) {
    int res = 0;
    Point[] surround = new Point[]{new Point(2, 1), new Point(1, 2), new Point(2, 3), new Point(3, 2)};
    if (layers.containsKey(layer + 1)) {
      List<Supplier<Boolean>> conditions = Arrays.asList(() -> pos.y == 0, () -> pos.x == 0, () -> pos.y == 4, () -> pos.x == 4);
      res += IntStream.range(0, conditions.size()).filter(i -> conditions.get(i).get()).map(i -> num(layers.get(layer + 1), surround[i])).sum();
    }
    if (layers.containsKey(layer - 1)) {
      List<Function<Integer, Point>> fs = Arrays.asList(i -> new Point(i, 0), i -> new Point(0, i), i -> new Point(i, 4), i -> new Point(4, i));
      res += IntStream.range(0, fs.size()).filter(i -> pos.equals(surround[i])).map(i -> nLayer(layer, fs.get(i))).sum();
    }
    return res + nAdjecent(layers.get(layer), pos);
  }

  public int nLayer(int layer, Function<Integer, Point> f) {
    return IntStream.range(0, 5).map(i -> num(layers.get(layer - 1), f.apply(i))).sum();
  }

  public char[][] fill(char[][] arr, char el) {
    streamGrid(arr).forEach(p -> set(arr, p, el));
    return arr;
  }
}
