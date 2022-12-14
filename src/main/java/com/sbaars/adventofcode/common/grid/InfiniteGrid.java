package com.sbaars.adventofcode.common.grid;

import com.sbaars.adventofcode.common.location.Loc;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class InfiniteGrid implements Grid {
  final Map<Loc, Character> grid;

  public InfiniteGrid(char[][] g) {
    this();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        grid.put(new Loc(j, i), g[i][j]);
      }
    }
  }

  public InfiniteGrid(Map<Loc, Character> grid) {
    this.grid = grid;
  }

  public InfiniteGrid() {
    this.grid = new HashMap<>();
  }

  public IntStream iterate() {
    return grid.values().stream().mapToInt(e -> e);
  }

  public Optional<Character> get(Loc p) {
    return grid.containsKey(p) ? of(grid.get(p)) : empty();
  }

  public void set(Loc p, char c) {
    grid.put(p, c);
  }

  public String toString() {
    long minX = grid.keySet().stream().mapToLong(e -> e.x).min().orElse(0);
    long minY = grid.keySet().stream().mapToLong(e -> e.y).min().orElse(0);
    long maxX = grid.keySet().stream().mapToLong(e -> e.x).max().orElse(0);
    long maxY = grid.keySet().stream().mapToLong(e -> e.y).max().orElse(0);
    CharGrid g = new CharGrid(' ', new Loc(maxX+1-minX, maxY+1-minY));
    grid.forEach((p, i) -> g.set(new Loc(p.x-minX, p.y-minY), i));
    return g.toString();
  }

  public static Collector<Loc, Map<Loc, Character>, InfiniteGrid> toInfiniteGrid(char c) {
    final Supplier<Map<Loc, Character>> supplier = HashMap::new;
    final BiConsumer<Map<Loc, Character>, Loc> accumulator = (a, b) -> a.put(b, c);
    final BinaryOperator<Map<Loc, Character>> combiner = (a, b) -> {a.putAll(b); return a;};
    final Function<Map<Loc, Character>, InfiniteGrid> finisher = a -> new InfiniteGrid(a);
    return Collector.of(supplier, accumulator, combiner, finisher);
  }
}
