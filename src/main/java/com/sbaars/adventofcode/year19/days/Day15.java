package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import com.sbaars.adventofcode.year19.pathfinding.Grid2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15 extends Day2019 {

  public static final int UNEXPLORED = 3;
  public static final int WALL = 0;
  private static final int PATH = 1;
  private static final int FINISH = 2;
  private static final int BOARD_SIZE = 41;
  private static final Loc START_POINT = new Loc(BOARD_SIZE / 2 + 1, BOARD_SIZE / 2 + 1);
  int[][] grid = new int[BOARD_SIZE][BOARD_SIZE];

  private final IntcodeComputer ic = new IntcodeComputer(15);

  public Day15() {
    super(15);
    for (int[] row : grid) Arrays.fill(row, UNEXPLORED);
    grid[START_POINT.intY()][START_POINT.intX()] = 1;
    Loc pos = START_POINT;
    while(pos != null) {
      explore(pos, ic);
      pos = moveToUnexploredPlace(pos, ic);
    }
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  @Override
  public Object part1() {
    Grid2d map2d = new Grid2d(grid, false);
    return map2d.findPath(START_POINT, findPos(FINISH).get(0)).size() - 1;
  }

  private Loc moveToUnexploredPlace(Loc pos, IntcodeComputer ic) {
    List<Loc> corridorSpaces = findPos(PATH);
    for (Loc p : corridorSpaces) {
      if (hasAdjacent(p, UNEXPLORED)) {
        Grid2d map2d = new Grid2d(grid, false);
        List<Loc> route = map2d.findPath(pos, p);
        traverseRoute(ic, pos, route.subList(1, route.size()));
        return p;
      }
    }
    return null;
  }

  private void traverseRoute(IntcodeComputer ic, Loc pos, List<Loc> route) {
    for (Loc p : route) {
      if (ic.run(Direction.getByMove(pos, p).num) != 1L)
        throw new IllegalStateException("Illegal state at " + pos + " execute to " + p);
      pos = p;
    }
  }

  private boolean hasAdjacent(Loc pos, int tile) {
    return grid[pos.intY() + 1][pos.intX()] == tile || grid[pos.intY()][pos.intX() + 1] == tile || grid[pos.intY() - 1][pos.intX()] == tile || grid[pos.intY()][pos.intX() - 1] == tile;
  }

  private List<Loc> findPos(int tile) {
    List<Loc> positions = new ArrayList<>();
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[y].length; x++) {
        if (grid[y][x] == tile) {
          positions.add(new Loc(x, y));
        }
      }
    }
    return positions;
  }

  private void explore(Loc pos, IntcodeComputer ic) {
    Direction dir = Direction.NORTH;
    for (int i = 0; i < 4; i++) {
      Loc move = dir.move(pos);
      if (grid[move.intY()][move.intX()] == UNEXPLORED) {
        grid[move.intY()][move.intX()] = Math.toIntExact(ic.run(dir.num));
        if (grid[move.intY()][move.intX()] != WALL) {
          ic.run(dir.opposite().num); // Move back
        }
      }
      dir = dir.turn(true);
    }
  }

  @Override
  public Object part2() {
    Loc oxygenLeak = findPos(FINISH).get(0);
    return findPos(PATH).stream().mapToInt(e -> new Grid2d(grid, false).findPath(oxygenLeak, e).size() - 1).max().getAsInt();
  }
}
