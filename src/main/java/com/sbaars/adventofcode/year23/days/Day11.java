package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.sbaars.adventofcode.util.AOCUtils.allPairs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.rangeClosed;

public class Day11 extends Day2023 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    Set<Long> emptyRows = grid.rows().entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
    Set<Long> emptyCols = grid.columns().entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
    return allPairs(grid.findAll('#').toList()).mapToLong(p -> p.a().distance(p.b()) + countEmpty(p, Loc::getX, emptyCols) + countEmpty(p, Loc::getY, emptyRows)).sum();
  }

  private long countEmpty(Pair<Loc, Loc> p, Function<Loc, Long> func, Set<Long> emptyRows) {
    long coord1 = func.apply(p.a());
    long coord2 = func.apply(p.b());
    return rangeClosed(min(coord1, coord2), max(coord1, coord2)).filter(emptyRows::contains).count();
  }

  @Override
  public Object part2() {
    return "";
  }
}
