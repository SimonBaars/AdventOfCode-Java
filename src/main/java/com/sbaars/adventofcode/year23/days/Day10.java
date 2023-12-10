package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.four;

public class Day10 extends Day2023 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    Loc start = grid.findAll('S').findAny().get();
    Direction comeFrom = four().filter(d -> grid.get(d.move(start)).isPresent()).filter(d -> connections(grid.get(d.move(start)).get()).anyMatch(d2 -> d2.equals(d.turnDegrees(180)))).findAny().get();
    Loc currentLoc = comeFrom.move(start);
    int numWalked = 1;
    while (!currentLoc.equals(start)) {
      Direction nextDir = comeFrom;
      comeFrom = connections(grid.get(currentLoc).get()).filter(d -> !d.equals(nextDir.turnDegrees(180))).findAny().get();
      currentLoc = comeFrom.move(currentLoc);
      numWalked++;
    }
    return numWalked / 2;
  }

  public Stream<Direction> connections(char c) {
    return switch (c) {
      case 'F' -> Stream.of(Direction.EAST, Direction.SOUTH);
      case '7' -> Stream.of(Direction.SOUTH, Direction.WEST);
      case 'L' -> Stream.of(Direction.NORTH, Direction.EAST);
      case 'J' -> Stream.of(Direction.NORTH, Direction.WEST);
      case '-' -> Stream.of(Direction.EAST, Direction.WEST);
      case '|' -> Stream.of(Direction.NORTH, Direction.SOUTH);
      default -> Stream.empty();
    };
  }

  @Override
  public Object part2() {
//    this.example = 13;
    var grid = new InfiniteGrid(dayGrid());
    var visited = new InfiniteGrid(' ', grid.width() * 2, grid.height() * 2);
    Loc start = grid.findAll('S').findAny().get();
    Direction comeFrom = four().filter(d -> grid.get(d.move(start)).isPresent()).filter(d -> connections(grid.get(d.move(start)).get()).anyMatch(d2 -> d2.equals(d.turnDegrees(180)))).findAny().get();
    Loc currentLoc = comeFrom.move(start);
    visited.set(currentLoc.translate(l -> l * 2), 'X');
    visited.set(comeFrom.turnDegrees(180).move(currentLoc.translate(l -> l * 2)), 'X');
    while (!currentLoc.equals(start)) {
      Direction nextDir = comeFrom;
      comeFrom = connections(grid.get(currentLoc).get()).filter(d -> !d.equals(nextDir.turnDegrees(180))).findAny().get();
      currentLoc = comeFrom.move(currentLoc);
      Loc zoomed = currentLoc.translate(l -> l * 2);
      visited.set(zoomed, 'X');
      Loc prevPos = comeFrom.turnDegrees(180).move(zoomed);
      visited.set(prevPos, 'X');
    }

    var pathGrid = new InfiniteGrid('.', grid.width(), grid.height());
    visited.findAll('X').forEach(l -> pathGrid.set(l.translate(l2 -> l2 / 2), 'X'));

    var borderGrid = pathGrid.withBorder(1, '.');
    var borderVisited = visited.withBorder(2, ' ');
    System.out.println(borderGrid);
    System.out.println(borderVisited);
    System.out.println(new InfiniteGrid(borderVisited.floodFill(new Loc(0, 0), c -> c == ' '), 'X'));
    System.out.println((-1 % 2));
    return borderGrid.findAll('.').count() - borderVisited.floodFill(new Loc(0, 0), c -> c == ' ').stream().filter(l -> l.x % 2 == 0 && l.y % 2 == 0).count();
//    System.out.println(new InfiniteGrid(visited.floodFill(new Loc(0, 0), c -> c == ' '), 'X'));
//    return grid
//        .findAll('|')
//        .flatMap(l -> Stream.of(new Loc(l.x * 2 + 1, l.y * 2), new Loc(l.x * 2 - 1, l.y * 2)))
//        .filter(l -> visited.contains(l) && visited.get(l).get() == ' ')
//        .map(l -> visited.floodFill(l, c -> c == ' '))
//        .filter(locs -> !locs.contains(new Loc(0, 0)))
//        .distinct()
//        .flatMap(Set::stream)
//        .filter(l -> l.x % 2 == 0 && l.y % 2 == 0)
//        .count();
  }
}
