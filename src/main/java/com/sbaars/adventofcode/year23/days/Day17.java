package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Arrays;
import java.util.PriorityQueue; 

import static com.sbaars.adventofcode.common.Direction.*;

public class Day17 extends Day2023 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  record State(Direction dir, int straightMoves) {}
  record Cell(Loc loc, long distance, State state) implements Comparable<Cell> {
    @Override
    public int compareTo(Cell other) {
      return Long.compare(distance, other.distance);
    }
  }

  static long shortestPath(NumGrid g, boolean part1) {
    int minStraight = part1 ? 0 : 4;
    int maxStraight = part1 ? 3 : 10;
    var dist = new long[g.sizeX()][g.sizeY()][Direction.values().length][maxStraight + 1];
    
    for (var d : dist) for (var row : d) for (var dirs : row) Arrays.fill(dirs, Long.MAX_VALUE);
    
    var pq = new PriorityQueue<Cell>();
    for (Direction dir : new Direction[]{EAST, SOUTH}) {
      dist[0][0][dir.ordinal()][0] = 0;
      pq.add(new Cell(new Loc(0, 0), 0, new State(dir, 0)));
    }
    
    long result = Long.MAX_VALUE;
    while (!pq.isEmpty()) {
      Cell curr = pq.poll();
      
      if (curr.loc.intX() == g.sizeX() - 1 && curr.loc.intY() == g.sizeY() - 1) {
        if (curr.state.straightMoves >= minStraight) {
          result = Math.min(result, curr.distance);
        }
        continue;
      }
      
      if (curr.distance > dist[curr.loc.intX()][curr.loc.intY()][curr.state.dir.ordinal()][curr.state.straightMoves]) {
        continue;
      }
      
      for (int turn = -1; turn <= 1; turn++) {
        Direction nextDir = turn == 0 ? curr.state.dir : curr.state.dir.turn(turn > 0);
        
        if (turn != 0 && curr.state.straightMoves < minStraight) continue;
        if (turn == 0 && curr.state.straightMoves >= maxStraight) continue;
        
        Loc nextLoc = nextDir.move(curr.loc);
        if (nextLoc.x < 0 || nextLoc.x >= g.sizeX() || nextLoc.y < 0 || nextLoc.y >= g.sizeY()) continue;
        
        int nextStraight = turn == 0 ? curr.state.straightMoves + 1 : 1;
        long nextDist = curr.distance + g.grid[nextLoc.intX()][nextLoc.intY()];
        
        if (nextDist < dist[nextLoc.intX()][nextLoc.intY()][nextDir.ordinal()][nextStraight]) {
          dist[nextLoc.intX()][nextLoc.intY()][nextDir.ordinal()][nextStraight] = nextDist;
          pq.add(new Cell(nextLoc, nextDist, new State(nextDir, nextStraight)));
        }
      }
    }
    return result;
  }

  @Override
  public Object part1() {
    return shortestPath(new NumGrid(day(), "\n", ""), true);
  }

  @Override
  public Object part2() {
    return shortestPath(new NumGrid(day(), "\n", ""), false);
  }
}
