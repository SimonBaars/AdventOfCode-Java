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
    return "";
  }
}
