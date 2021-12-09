package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.Direction.fourDirections;
import static java.lang.Math.toIntExact;
import static java.util.Arrays.stream;
import static java.util.Collections.reverseOrder;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Day9 extends Day2021 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  @Override
  public Object part1() {
    NumGrid in = new NumGrid(day(), "\n", "");
    var grid = in.grid;
    return in.stream()
        .filter(p -> isLowPoint(grid, p))
        .mapToLong(p -> 1+grid[p.x][p.y])
        .sum();
  }

  @Override
  public Object part2() {
    NumGrid in = new NumGrid(day(), "\n", "");
    var grid = in.grid;
    return in.stream()
        .filter(p -> isLowPoint(grid, p))
        .map(p -> findBasins(new HashSet<>(), in, p, -1))
        .sorted(reverseOrder())
        .limit(3)
        .reduce((a,b) -> a*b)
        .get();
  }

  private boolean isLowPoint(long[][] in, Point loc) {
    return stream(fourDirections()).map(d -> d.getInGrid(in, loc, -1)).filter(n -> n != -1).allMatch(n -> n > in[loc.x][loc.y]);
  }

  private int findBasins(Set<Point> illegal, NumGrid in, Point loc, int height) {
    int amount = 0;
    if(in.get(loc) != -1 && !illegal.contains(loc) && in.get(loc) > height && in.get(loc) < 9){
      amount+=stream(fourDirections()).map(d -> d.move(loc)).filter(e -> in.get(loc) != -1).mapToInt(p -> findBasins(illegal, in, p, toIntExact(in.get(loc)))).sum() + 1;
      illegal.add(loc);
    }
    return amount;
  }
}
