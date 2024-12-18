package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.util.DataMapper;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.AoCUtils.recurse;

import java.util.*;

public class Day18 extends Day2024 {
  public static final long BOUND = 70;

  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  @Override
  public Object part1() {
    return nSteps(input(), 1024L);
  }

  @Override
  public Object part2() {
    var input = input();
    for (int i = 1025; ; i++) {
      try {
        nSteps(input, i);
      } catch (Exception e) {
        return input.get(i - 1).toString().replace(" ", "");
      }
    }
  }

  private List<Loc> input() {
    return dayStream().map(s -> DataMapper.readString(s, "%n,%n", Loc.class)).toList();
  }

  private int nSteps(List<Loc> input, long limit) {
    InfiniteGrid g = new InfiniteGrid('.', BOUND + 1, BOUND + 1);
    input.stream()
      .limit(limit)
      .forEach(l -> g.set(l, '#'));
    Set<Loc> visited = new HashSet<>();
    return recurse(new State(new Loc(0, 0), 0), s -> s.loc().equals(new Loc(BOUND, BOUND)), (stack, current) -> {
      Direction.four().map(d -> current.loc().move(d))
        .filter(g::contains)
        .filter(l -> !visited.contains(l))
        .filter(l -> g.getOptimistic(l) != '#')
        .forEach(l -> {
          visited.add(l);
          stack.add(new State(l, current.steps() + 1));
        });
    }).steps();
  }

  public record State(Loc loc, int steps) {}
}
