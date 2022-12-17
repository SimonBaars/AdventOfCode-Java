package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static java.lang.Math.toIntExact;

public class Day17 extends Day2022 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  public record State(long height, long fallenRocks, boolean cycleReset) {}

  @Override
  public Object part1() {
    return simulateShapeMoves(2022, s -> false).height;
  }

  @Override
  public Object part2() {
    long iterations = 1000000000000L;
    State cycleStart = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks != 0);
    State nextCycle = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks > cycleStart.fallenRocks);
    long rocks = cycleStart.fallenRocks;
    long height = cycleStart.height;
    while(rocks<iterations){
      rocks += nextCycle.fallenRocks - cycleStart.fallenRocks;
      height += nextCycle.height - cycleStart.height;
    }
    long overshoot = rocks - iterations;
    State atOvershoot = simulateShapeMoves(iterations, s -> s.fallenRocks == cycleStart.fallenRocks - overshoot);
    return height - (cycleStart.height - atOvershoot.height);
  }

  private State simulateShapeMoves(long iterations, Predicate<State> exitCondition) {
    InfiniteGrid[] shapes = new InfiniteGrid[]{
            new InfiniteGrid(new char[][]{"####".toCharArray()}),
            new InfiniteGrid(new HashMap<>(Map.of(new Loc(1, 0), '#', new Loc(0, 1), '#', new Loc(1, 1), '#', new Loc(2, 1), '#', new Loc(1, 2), '#'))),
            new InfiniteGrid(new HashMap<>(Map.of(new Loc(2, 0), '#', new Loc(0, 2), '#', new Loc(1, 2), '#', new Loc(2, 2), '#', new Loc(2, 1), '#'))),
            new InfiniteGrid(new char[][]{{'#'}, {'#'}, {'#'}, {'#'}}),
            new InfiniteGrid(new char[][]{{'#', '#'}, {'#', '#'}})
    };
    InfiniteGrid g = new InfiniteGrid(new char[][]{"+-------+".toCharArray()});
    addWall(g, 4);
    char[] chars = day().trim().toCharArray();
    long highest = 0, fallenRocks = 0;
    InfiniteGrid s = shapes[0].move(3, highest - 4);
    for(int i = 0, shapeIndex = 0; fallenRocks<iterations; i++) {
      if(i>=chars.length) i = 0;

      State state = new State(Math.abs(highest), fallenRocks, i==0);
      if(exitCondition.test(state)) {
        return state;
      }

      char c = chars[i];
      InfiniteGrid moved = s.move(c == '>' ? 1 : -1, 0);
      if(g.canPlace(moved)) {
        s = moved;
      }

      moved = s.move(0, 1);
      if(g.canPlace(moved)) {
        s = moved;
      } else {
        g.place(s);
        shapeIndex = (shapeIndex + 1) % shapes.length;
        long oldHeighest = highest;
        highest = Math.min(s.minY(), highest);
        addWall(g, toIntExact((oldHeighest - highest) + shapes[shapeIndex].height()));
        s = shapes[shapeIndex].move(3, highest - 3 - shapes[shapeIndex].height());
        fallenRocks++;
      }
    }
    return new State(Math.abs(highest), iterations, false);
  }

  private void addWall(InfiniteGrid g, int n) {
    long lowestY = g.minY()-1;
    for(int i = 0; i<n; i++){
      g.set(new Loc(0, lowestY-i), '|');
      g.set(new Loc(8, lowestY-i), '|');
    }
  }
}
