package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

public class Day4 extends Day2024 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  @Override
  public Object part1() {
    InfiniteGrid grid = new InfiniteGrid(dayGrid());
    return grid.stream()
        .filter(p -> grid.getOptimistic(p) == 'X')
        .flatMap(l -> l.eightDirs()
            .map(dir -> {
              StringBuilder s = new StringBuilder();
              Loc curr = dir;
              for (int i = 0; i < 3 && grid.contains(curr); i++) {
                s.append(grid.getOptimistic(curr));
                curr = curr.move(dir.x - l.x, dir.y - l.y);
              }
              return s.toString();
            }))
        .filter("MAS"::equals)
        .count();
  }

  @Override
  public Object part2() {
    InfiniteGrid grid = new InfiniteGrid(dayGrid());
    return grid.stream()
        .parallel()
        .filter(p -> grid.getOptimistic(p) == 'A')
        .filter(l -> l.x > 0 && l.y > 0 && l.x < grid.width()-1 && l.y < grid.height()-1)
        .filter(l -> {
          String diag1 = "" + grid.getOptimistic(l.move(1, -1)) + grid.getOptimistic(l.move(-1, 1));
          String diag2 = "" + grid.getOptimistic(l.move(-1, -1)) + grid.getOptimistic(l.move(1, 1));
          return (diag1.equals("MS") || diag1.equals("SM")) && (diag2.equals("MS") || diag2.equals("SM"));
        })
        .count();
  }
}
