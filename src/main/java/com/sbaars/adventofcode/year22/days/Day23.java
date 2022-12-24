package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day23 extends Day2022 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  @Override
  public Object part1() {
    return solution(false);
  }

  @Override
  public Object part2() {
    return solution(true);
  }

  public long solution(boolean isPart2) {
    Builder<InfiniteGrid> b = new Builder<>(new InfiniteGrid(dayGrid(), '.'), InfiniteGrid::new);
    List<Direction> dirs = new ArrayList<>(List.of(NORTH, SOUTH, WEST, EAST));
    for(int i = 0; isPart2 || i<10; i++) {
      InfiniteGrid g = b.get();
      Map<Loc, Loc> dest = g.stream()
              .filter(e -> Direction.eight().anyMatch(d -> g.contains(d.move(e))))
              .flatMap(e -> dirs.stream().filter(d -> !g.contains(d.move(e)) && !g.contains(d.turn().move(d.move(e)))  && !g.contains(d.turn(false).move(d.move(e)))).map(d -> new Pair<>(e, d.move(e))).limit(1))
              .collect(Collectors.toMap(Pair::a, Pair::b));
      dest.entrySet().stream()
              .map(e -> dest.values().stream().filter(l -> l.equals(e.getValue())).limit(2).count() == 1 ? e.getValue() : e.getKey())
              .forEach(e -> b.getNew().set(e, '#'));
      g.grid.keySet().stream()
              .filter(e -> !dest.containsKey(e))
              .forEach(e -> b.getNew().set(e, '#'));
      dirs.add(dirs.remove(0));
      if(b.getNew().grid.keySet().equals(g.grid.keySet())){
        return i+1;
      }
      b.refresh();
    }
    return b.get().toString().chars().filter(c -> c == ' ').count();
  }
}
