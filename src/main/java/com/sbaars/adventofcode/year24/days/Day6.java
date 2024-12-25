package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.grid.Walker;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Direction.NORTH;

import java.util.*;

public class Day6 extends Day2024 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    Loc startLoc = grid.findAll('^').findFirst().get();
    Walker walker = new Walker(startLoc, NORTH);
    Set<Loc> path = path(grid, walker);
    return path.size();
  }

  private Set<Loc> path(InfiniteGrid g, Walker walker) {
    Set<Loc> visited = new HashSet<>();
    while (g.contains(walker.loc())) {
      char current = g.getChar(walker.loc());
      if (current == '#') {
        walker = walker.walkBack().turnRight();
      } else {
        visited.add(walker.loc());
        walker = walker.walk();
      }
    }
    return visited;
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    Loc startLoc = grid.findAll('^').findFirst().get();
    Set<Loc> path = path(grid, new Walker(startLoc, NORTH));
    return path.stream().filter(l -> grid.getChar(l) == '.')
        .filter(l -> hasLoop(grid.withReplaced(l, '#'), new Walker(startLoc, NORTH))).count();
  }

  private boolean hasLoop(InfiniteGrid grid, Walker walker) {
    Set<Walker> visitedStates = new HashSet<>();
    while (grid.contains(walker.loc())) {
      char current = grid.getChar(walker.loc());
      if (current == '#') {
        walker = walker.walkBack().turnRight();
      } else {
        if (visitedStates.contains(walker)) {
          return true;
        }
        visitedStates.add(walker);
        walker = walker.walk();
      }
    }
    return false;
  }
}
