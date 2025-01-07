package com.sbaars.adventofcode.year23.days;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;
import java.util.*;

import static com.sbaars.adventofcode.common.Direction.*;

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
    tilt(grid, NORTH);
    return calculateLoad(grid);
  }

  private void tilt(InfiniteGrid grid, Direction dir) {
    boolean ascending = dir == NORTH || dir == WEST;
    boolean vertical = dir == NORTH || dir == SOUTH;
    long start = vertical ? grid.minY() : grid.minX();
    long end = vertical ? grid.maxY() : grid.maxX();
    long len = vertical ? grid.width() : grid.height();
    
    for (long i = 0; i < len; i++) {
      long pos = ascending ? start : end;
      for (long j = ascending ? start : end; ascending ? j <= end : j >= start; j += ascending ? 1 : -1) {
        Loc current = vertical ? new Loc(i, j) : new Loc(j, i);
        if (grid.getChar(current) == '#') {
          pos = j + (ascending ? 1 : -1);
        } else if (grid.getChar(current) == 'O') {
          if (j != pos) {
            Loc target = vertical ? new Loc(i, pos) : new Loc(pos, i);
            grid.set(current, '.');
            grid.set(target, 'O');
          }
          pos += ascending ? 1 : -1;
        }
      }
    }
  }

  private long calculateLoad(InfiniteGrid grid) {
    return grid.findAll('O').mapToLong(l -> grid.maxY() - l.y + 1).sum();
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    Map<String, Integer> seen = new HashMap<>();
    int target = 1000000000;
    
    for (int i = 0; i < target; i++) {
      cycle(grid);
      String state = grid.toString();
      
      Integer previous = seen.put(state, i);
      if (previous != null) {
        int cycleLength = i - previous;
        int remaining = (target - i - 1) % cycleLength;
        for (int j = 0; j < remaining; j++) cycle(grid);
        break;
      }
    }
    return calculateLoad(grid);
  }

  private void cycle(InfiniteGrid grid) {
    tilt(grid, NORTH);
    tilt(grid, WEST);
    tilt(grid, SOUTH);
    tilt(grid, EAST);
  }
}
