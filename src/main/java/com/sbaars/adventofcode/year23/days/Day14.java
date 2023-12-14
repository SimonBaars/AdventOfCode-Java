package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.util.Solver;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.NORTH;
import static java.lang.Math.abs;

public class Day14 extends Day2023 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    turn(grid, NORTH);
    return calculateSum(grid);
  }

  private static long calculateSum(InfiniteGrid grid) {
    return grid.findAll('O').mapToLong(l -> abs(l.y - grid.maxY()) + 1).sum();
  }

  private void turn(InfiniteGrid grid, Direction dir) {
    grid.findAll('O').forEach(r -> {
      Loc loc = r;
      while (grid.get(dir.move(loc)).map(c -> c == '.').orElse(false)) {
        loc = dir.move(loc);
      }
      grid.set(r, '.');
      grid.set(loc, 'O');
    });
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    Direction d = NORTH;
    return Solver.solve(Stream.iterate(new Pair<>(NORTH, grid), this::doTurn), p -> calculateSum(p.b()), 1000000000L);
  }

  private Pair<Direction, InfiniteGrid> doTurn(Pair<Direction, InfiniteGrid> pair) {
    turn(pair.b(), pair.a());
    return new Pair<>(pair.a().turn(), pair.b());
  }
}
