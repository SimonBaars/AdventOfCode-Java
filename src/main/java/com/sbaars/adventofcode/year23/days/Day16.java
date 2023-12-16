package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.common.Pair.pair;

public class Day16 extends Day2023 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  @Override
  public Object part1() {
    return solve(new InfiniteGrid(dayGrid()), new Pair<>(EAST, new Loc(0, 0)));
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    long maxX = grid.maxX();
    long maxY = grid.maxY();
    return grid
        .stream()
        .filter(l -> l.x == 0 || l.y == 0 || l.x == maxX || l.y == maxY)
        .flatMap(l -> ((l.x == 0 || l.x == maxX) && (l.y == 0 || l.y == maxY)) ? four().map(d -> pair(d, l)) : Stream.of(pair(l.x == 0 ? EAST : l.y == 0 ? SOUTH : l.x == maxX ? WEST : NORTH, l)))
        .mapToLong(p -> solve(grid, p))
        .max()
        .getAsLong();
  }

  private long solve(InfiniteGrid grid, Pair<Direction, Loc> start) {
    var beamQueue = new LinkedList<>(List.of(start));
    var visited = new HashSet<Pair<Direction, Loc>>();
    visited.add(beamQueue.peek());
    while (!beamQueue.isEmpty()) {
      var pair = beamQueue.poll();
      var loc = pair.b();
      var dir = pair.a();
      var newLoc = new ArrayList<Pair<Direction, Loc>>();
      if (grid.contains(loc)) {
        char curr = grid.getChar(loc);
        if (curr == '.' || (curr == '|' && (dir == NORTH || dir == SOUTH)) || (curr == '-' && (dir == EAST || dir == WEST))) {
          newLoc.add(pair(dir, dir.move(loc)));
        } else if (curr == '\\') {
          var newDir = dir.turn(dir == EAST || dir == WEST);
          newLoc.add(pair(newDir, newDir.move(loc)));
        } else if (curr == '/') {
          var newDir = dir.turn(dir == NORTH || dir == SOUTH);
          newLoc.add(pair(newDir, newDir.move(loc)));
        } else if (curr == '|' && (dir == EAST || dir == WEST)) {
          newLoc.add(pair(dir.turn(false), dir.turn(false).move(loc)));
          newLoc.add(pair(dir.turn(true), dir.turn(true).move(loc)));
        } else if (curr == '-' && (dir == NORTH || dir == SOUTH)) {
          newLoc.add(pair(dir.turn(false), dir.turn(false).move(loc)));
          newLoc.add(pair(dir.turn(true), dir.turn(true).move(loc)));
        }
      }
      for (var newPair : newLoc) {
        if (!visited.contains(newPair) && grid.contains(newPair.b())) {
          beamQueue.add(newPair);
          visited.add(newPair);
        }
      }
    }
    return visited.stream().map(Pair::b).distinct().count();
  }
}
