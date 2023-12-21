package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.sbaars.adventofcode.util.AoCUtils.allPairs;
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
    return solve(true);
  }

  @Override
  public Object part2() {
    return solve(false);
  }

  private long solve(boolean part1) {
    var grid = new InfiniteGrid(dayGrid());
    Set<Long> emptyRows = countEmpty(grid.rows(), grid);
    Set<Long> emptyCols = countEmpty(grid.columns(), grid);
    return allPairs(grid.findAll('#').toList())
        .mapToLong(p -> p.a().distance(p.b()) + countEmpty(p, Loc::getX, emptyCols, part1) + countEmpty(p, Loc::getY, emptyRows, part1))
        .sum();
  }

  private static Set<Long> countEmpty(Map<Long, List<Loc>> rowsOrCols, InfiniteGrid grid) {
    return rowsOrCols.entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
  }

  private long countEmpty(Pair<Loc, Loc> p, Function<Loc, Long> func, Set<Long> emptyRows, boolean part1) {
    long coord1 = func.apply(p.a());
    long coord2 = func.apply(p.b());
    return rangeClosed(min(coord1, coord2), max(coord1, coord2)).filter(emptyRows::contains).count() * (part1 ? 1 : (1000000 - 1));
  }
}
