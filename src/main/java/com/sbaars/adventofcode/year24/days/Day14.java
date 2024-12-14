package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.BoundedLoc;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.util.AoCUtils.appendWhile;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day14 extends Day2024 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  @Override
  public Object part1() {
    return countBuckets(step(parseInput(), 100), 101, 103);
  }

  @Override
  public Object part2() {
    return appendWhile(r -> step(r, 1), this::notTree, parseInput()).count();
  }

  private List<Robot> parseInput() {
    return dayStream().map(s -> readString(s, "p=%n,%n v=%n,%n", Robot.class)).toList();
  }

  private List<Robot> step(List<Robot> robots, long seconds) {
    return robots.stream().map(robot -> robot.move(seconds)).toList();
  }

  private long countBuckets(List<Robot> robots, long width, long height) {
    long midX = width / 2, midY = height / 2;
    Map<Long, Long> quadrants = robots.stream()
        .filter(robot -> robot.p().x != midX && robot.p().y != midY)
        .collect(groupingBy(r -> (r.p().x / ((width + 1) / 2)) * 2 + (r.p().y / ((height + 1) / 2)), counting()));
    return quadrants.values().stream().reduce(1L, (a, b) -> a * b);
  }

  private boolean notTree(List<Robot> robots) {
    Set<Loc> tree = new InfiniteGrid("""
            ^
           ^^^
          ^o^^^
         ^^^^o^^
            ^
        """).grid.keySet();
    Set<Loc> grid = robots.stream().map(Robot::p).collect(toSet());
    return grid.stream().noneMatch(offset -> tree.stream().allMatch(loc -> grid.contains(loc.move(offset))));
  }

  public record Robot(Loc p, Loc v) {
    public Robot(long x, long y, long vx, long vy) {
      this(new BoundedLoc(x, y, 101, 103), new Loc(vx, vy));
    }

    public Robot move(long seconds) {
      return new Robot(p.move(v.translate(x -> x * seconds)), v);
    }
  }
}
