package com.sbaars.adventofcode.year24.days;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static com.sbaars.adventofcode.common.Direction.WEST;
import static com.sbaars.adventofcode.util.AoCUtils.alternating;
import static com.sbaars.adventofcode.util.AoCUtils.recurse;
import static java.util.stream.Stream.concat;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

public class Day15 extends Day2024 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  @Override
  public Object part1() {
    String in = day();
    String[] parts = in.split("\n\n");
    InfiniteGrid g = new InfiniteGrid(parts[0]);
    return solve(g, parts[1]);
  }

  @Override
  public Object part2() {
    String in = day();
    String[] parts = in.split("\n\n");
    InfiniteGrid g = new InfiniteGrid(parts[0]).zoom(2, 1);
    g.findAll('@').skip(1).forEach(l -> g.set(l, '.'));
    alternating(g.findAll('O').sorted((l1, l2) -> l1.intY() - l2.intY()), l -> g.set(l, '['), l -> g.set(l, ']'));
    return solve(g, parts[1]);
  }

  public static InfiniteGrid extractComponent(InfiniteGrid g, Loc startLoc, Direction initialDirection) {
    return recurse(new InfiniteGrid(), startLoc, (component, stack, currentLoc) -> {
      if (g.get(currentLoc).filter(c -> c == '#' || c == '.').isPresent()) return component;
      char ch = g.set(currentLoc, '.');
      component.set(currentLoc, ch);
      stack.add(currentLoc.move(initialDirection));
      if (ch == '[') {
        stack.add(currentLoc.move(EAST));
      } else if (ch == ']') {
        stack.add(currentLoc.move(WEST));
      }
      return component;
    });
  }

  public static long solve(InfiniteGrid g, String moves) {
    Loc robotPos = g.findAll('@').findAny().get();
    for (char move : moves.replace("\n", "").toCharArray()) {
      Direction d = Direction.caretToDirection(move);
      InfiniteGrid component = extractComponent(g, robotPos, d);
      if (component.stream().allMatch(loc -> g.get(loc.move(d)).filter(c -> c == '.').isPresent())) {
        component = component.move(d);
        robotPos = robotPos.move(d);
      }
      g.place(component);
    }
    return concat(g.findAll('O'), g.findAll('[')).mapToLong(l -> l.y * 100 + l.x).sum();
  }
}
