package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.io.IOException;

public class Day8 extends Day2022 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) throws IOException {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    NumGrid grid = new NumGrid(day(), "\n", "");
    return grid.stream().filter(p -> findEdge(grid, p)).count();
  }

  private boolean findEdge(NumGrid grid, Point p) {
    Direction[] dirs = Direction.fourDirections();
    long num = grid.get(p);
    for (Direction d : dirs) {
      Point newLoc = p;
      while (true) {
        newLoc = d.move(newLoc);
        long atLoc = grid.get(newLoc);
        if (atLoc >= num) break;
        else if (atLoc == -1) return true;
      }
    }
    return false;
  }

  @Override
  public Object part2() {
    NumGrid grid = new NumGrid(day(), "\n", "");
    return grid.stream().mapToLong(p -> scenicScore(grid, p)).max().getAsLong();
  }

  private long scenicScore(NumGrid grid, Point p) {
    Direction[] dirs = Direction.fourDirections();
    long num = grid.get(p);
    long score = 1;
    for (Direction d : dirs) {
      Point newLoc = p;
      long s = 0;
      while (true) {
        newLoc = d.move(newLoc);
        long atLoc = grid.get(newLoc);
        if (atLoc >= num) {
          s++;
          break;
        } else if (atLoc == -1) break;
        s++;
      }
      score *= s;
    }
    return score;
  }
}
