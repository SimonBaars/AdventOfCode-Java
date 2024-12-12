package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.TriFunction;

import static java.util.stream.Stream.concat;

import java.util.HashSet;
import java.util.LinkedList;
import static com.sbaars.adventofcode.util.AoCUtils.al;
import static com.sbaars.adventofcode.util.AoCUtils.appendWhile;

public class Day12 extends Day2024 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  @Override
  public Object part1() {
    return solve((grid, edge, direction) -> Stream.of());
  }

  @Override
  public Object part2() {
    return solve((grid, edge, direction) -> concat(explorePerimeter(grid, edge.a(), direction, grid.getChar(edge.a()), true), explorePerimeter(grid, edge.a(), direction, grid.getChar(edge.a()), false)));
  }

  record Edge(Loc a, Loc b) {}

  public long solve(TriFunction<InfiniteGrid, Edge, Direction, Stream<Edge>> lambda) {
    Set<Loc> visited = new HashSet<>();
    var stack = new LinkedList<Loc>();
    var grid = new InfiniteGrid(dayGrid());
    return grid.stream().filter(loc -> !visited.contains(loc)).mapToLong(loc -> {
      Set<Edge> visitedEdges = new HashSet<>();
      stack.clear();
      stack.push(loc);
      AtomicLong area = al(), perimeter = al();

      while (!stack.isEmpty()) {
        Loc current = stack.pop();
        if (visited.add(current)) {
          area.incrementAndGet();
          grid.walkAround(current).forEach((next, direction) -> {
            if (grid.getChar(next) == grid.getChar(loc)) {
              stack.push(next);
            } else {
              var edge = new Edge(current, next);
              if (visitedEdges.add(edge)) {
                perimeter.incrementAndGet();
                lambda.apply(grid, edge, direction).forEach(visitedEdges::add);
              }
            }
          });
        }
      }
      return area.get() * perimeter.get();
    }).sum();
  }

  private Stream<Edge> explorePerimeter(InfiniteGrid grid, Loc current, Direction direction, char charValue, boolean turnRight) {
    Direction newDirection = direction.turn(turnRight);
    return appendWhile(
      edge -> new Edge(edge.a().move(newDirection), edge.a().move(newDirection).move(direction)),
      edge -> grid.contains(edge.a()) && grid.getChar(edge.a()) == charValue && (!grid.contains(edge.b()) || grid.getChar(edge.b()) != charValue),
      new Edge(current.move(newDirection), current.move(newDirection).move(direction))
    );
  }
}
