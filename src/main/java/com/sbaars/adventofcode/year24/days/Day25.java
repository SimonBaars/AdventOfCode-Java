package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;

import static com.sbaars.adventofcode.util.MuUtils.allPairs;

import java.util.*;

public class Day25 extends Day2024 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  @Override
  public Object part1() {
    List<InfiniteGrid> locks = new ArrayList<>();
    List<InfiniteGrid> keys = new ArrayList<>();
    dayStream("\n\n").map(InfiniteGrid::new).forEach(g -> {
      long rows = g.height() - 1;
      if (g.stream().filter(l -> l.y == 0).allMatch(l -> g.getChar(l) == '#') && g.stream().filter(l -> l.y == rows).allMatch(l -> g.getChar(l) == '.')) {
        locks.add(g);
      } else if (g.stream().filter(l -> l.y == 0).allMatch(l -> g.getChar(l) == '.') && g.stream().filter(l -> l.y == rows).allMatch(l -> g.getChar(l) == '#')) {
        keys.add(g);
      }
    });
    return allPairs(locks, keys).filter((l, k) -> l.stream().noneMatch(loc -> l.get(loc).orElse(' ') == '#' && k.get(loc).orElse(' ') == '#')).count();
  }

  @Override
  public Object part2() {
    return "The End.";
  }
}
