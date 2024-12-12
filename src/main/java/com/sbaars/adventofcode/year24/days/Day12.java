package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.MutableLoc;
import com.sbaars.adventofcode.year24.Day2024;

import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import static com.sbaars.adventofcode.common.Direction.four;
import static com.sbaars.adventofcode.common.Direction.fourDirections;
import static com.sbaars.adventofcode.util.AoCUtils.al;
import static com.sbaars.adventofcode.util.AoCUtils.appendWhile;
import static com.sbaars.adventofcode.common.Pair.pair;

public class Day12 extends Day2024 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  @Override
  public Object part1() {
    Set<Loc> visited = new HashSet<>();
    var stack = new LinkedList<Loc>();
    var g = new InfiniteGrid(dayGrid());

    return g.streamChars()
            .filter((l, c) -> !visited.contains(l))
            .mapToLong((loc, c) -> {
              AtomicLong area = al(), perimeter = al();
              stack.push(loc);

              while (!stack.isEmpty()) {
                Loc curr = stack.pop();
                if (visited.add(curr)) {
                  area.incrementAndGet();
                  g.walkAround(curr).forEach((l2, d) -> {
                    if (g.getChar(l2) == c) {
                      stack.push(l2);
                    } else {
                      perimeter.incrementAndGet();
                    }
                  });
                }
              }
              return area.get() * perimeter.get();
            }).sum();
  }

  @Override
  public Object part2() {
    Set<Loc> visited = new HashSet<>();
    var stack = new LinkedList<Loc>();
    var g = new InfiniteGrid(dayGrid());
    return g.streamChars()
            .filter((l, c) -> !visited.contains(l))
            .mapToLong((p, c) -> {
        Set<Edge> visited2 = new HashSet<>();
        stack.clear();
        stack.push(p);
        AtomicLong area = al(), perimeter = al();

        while (!stack.isEmpty()) {
          Loc curr = stack.pop();
          if (visited.add(curr)) {
            area.incrementAndGet();
            g.walkAround(curr).forEach((next, d) -> {
              if (g.getChar(next) == c) {
                stack.push(next);
              } else {
                var edge = new Edge(curr, next);
                if (visited2.add(edge)) {
                  perimeter.incrementAndGet();
                  explorePerimeter(g, edge.a(), d, c, true).forEach(visited2::add);
                  explorePerimeter(g, edge.a(), d, c, false).forEach(visited2::add);
                }
              }
            });
          }
        }
        return area.get() * perimeter.get();
      }).sum();
    // solve((g, data) -> {
  }

  record Edge(Loc a, Loc b) {}
  public record Data(Set<Edge> visited2, Edge edge, Direction d) {}

  private Stream<Edge> explorePerimeter(InfiniteGrid g, Loc curr, Direction d, char c, boolean turnRight) {
      Direction d2 = d.turn(turnRight);
      return appendWhile(
          p -> new Edge(p.a().move(d2), p.a().move(d2).move(d)),
          p -> g.contains(p.a()) && g.getChar(p.a()) == c && (!g.contains(p.b()) || g.getChar(p.b()) != c),
          new Edge(curr.move(d2), curr.move(d2).move(d))
      );
  }
}
