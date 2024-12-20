package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.google.mu.util.stream.BiStream;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import java.util.*;

public class Day20 extends Day2024 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  @Override
  public Object part1() {
    return solve(2);
  }

  @Override
  public Object part2() {
    return solve(20);
  }

  private long solve(int maxSteps) {
    InfiniteGrid g = new InfiniteGrid(dayGrid());
    Loc start = g.find('S');
    Loc end = g.find('E');
    Map<Loc, Integer> distStart = g.bfs(start, '#');
    Map<Loc, Integer> distEnd = g.bfs(end, '#');
    int normalDist = distStart.get(end);
    return BiStream.from(distStart).flatMap((loc, dist) ->
      g.bfsStream(loc, maxSteps)
        .filter((l, s) -> s <= maxSteps && (g.getChar(l) == '.' || l.equals(end)))
        .filter((l, s) -> normalDist - (dist + s + distEnd.get(l)) >= 100)
    ).count();
  }
}
