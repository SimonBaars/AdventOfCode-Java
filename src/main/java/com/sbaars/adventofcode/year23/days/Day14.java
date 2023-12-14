package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.util.Solver.solve;
import static java.lang.Math.abs;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;

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
    grid.findAll('O').sorted(dir == EAST || dir == SOUTH ? reverseOrder() : naturalOrder()).forEach(r -> {
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
    return solve(Stream.iterate(grid, this::doTurn), Day14::calculateSum, 1000000000L);
  }

  private InfiniteGrid doTurn(InfiniteGrid grid) {
    Stream.of(NORTH, WEST, SOUTH, EAST).forEach(d -> turn(grid, d));
    return grid;
  }
}
