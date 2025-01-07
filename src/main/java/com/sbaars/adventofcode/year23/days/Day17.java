package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Comparator;
import java.util.PriorityQueue;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static com.sbaars.adventofcode.common.Direction.SOUTH;

public class Day17 extends Day2023 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  public record State(Direction dir, int straightMoves) {}

  public record Cell(Loc loc, long distance, State state) implements Comparable<Cell> {
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Cell cell)) return false;
      return loc.equals(cell.loc) && state.equals(cell.state);
    }

    @Override
    public int compareTo(Cell other) {
      return Long.compare(distance, other.distance);
    }
  }

  static long shortestPath(NumGrid g, int row, int col, boolean part1) {
    long[][] grid = g.grid;
    int minStraight = part1 ? 0 : 4;
    int maxStraight = part1 ? 3 : 10;
    
    var dist = new long[g.sizeX()][g.sizeY()][Direction.values().length][maxStraight + 1];
    
    for (int i = 0; i < dist.length; i++) {
      for (int j = 0; j < dist[0].length; j++) {
        for (int d = 0; d < Direction.values().length; d++) {
          for (int s = 0; s <= maxStraight; s++) {
            dist[i][j][d][s] = Long.MAX_VALUE;
          }
        }
      }
    }
    
    var pq = new PriorityQueue<Cell>(Comparator.comparing(Cell::distance));
    
    for (Direction dir : new Direction[]{EAST, SOUTH}) {
      dist[0][0][dir.ordinal()][0] = 0;
      pq.add(new Cell(new Loc(0, 0), 0, new State(dir, 0)));
    }
    
    long result = Long.MAX_VALUE;
    while (!pq.isEmpty()) {
      Cell curr = pq.poll();
      
      if (curr.loc.intX() == grid.length - 1 && curr.loc.intY() == grid[0].length - 1) {
        if (curr.state.straightMoves >= minStraight) {
          result = Math.min(result, curr.distance);
        }
        continue;
      }
      
      if (curr.distance > dist[curr.loc.intX()][curr.loc.intY()][curr.state.dir.ordinal()][curr.state.straightMoves]) {
        continue;
      }
      
      for (int turn = -1; turn <= 1; turn++) {
        Direction nextDir = turn == 0 ? curr.state.dir : (turn == 1 ? curr.state.dir.turn(true) : curr.state.dir.turn(false));
        
        if (turn != 0) {
          if (curr.state.straightMoves < minStraight) continue;
        } else {
          if (curr.state.straightMoves >= maxStraight) continue;
        }
        
        Loc nextLoc = nextDir.move(curr.loc);
        if (!isInsideGrid(nextLoc, grid.length, grid[0].length)) continue;
        
        int nextStraight = turn == 0 ? curr.state.straightMoves + 1 : 1;
        long nextDist = curr.distance + grid[nextLoc.intX()][nextLoc.intY()];
        
        if (nextDist < dist[nextLoc.intX()][nextLoc.intY()][nextDir.ordinal()][nextStraight]) {
          dist[nextLoc.intX()][nextLoc.intY()][nextDir.ordinal()][nextStraight] = nextDist;
          pq.add(new Cell(nextLoc, nextDist, new State(nextDir, nextStraight)));
        }
      }
    }
    
    return result;
  }

  static boolean isInsideGrid(Loc l, int sizex, int sizey) {
    return l.x >= 0 && l.x < sizex && l.y >= 0 && l.y < sizey;
  }

  @Override
  public Object part1() {
    var in = new NumGrid(day(), "\n", "");
    return shortestPath(in, in.sizeX() - 1, in.sizeY() - 1, true);
  }

  @Override
  public Object part2() {
    var in = new NumGrid(day(), "\n", "");
    return shortestPath(in, in.sizeX() - 1, in.sizeY() - 1, false);
  }
}
