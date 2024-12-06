package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static java.lang.Long.MAX_VALUE;

import java.util.*;

import org.apache.commons.lang3.tuple.Triple;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day6 extends Day2024 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    var obstructionData = buildObstructionData(grid);
    var guardStart = findGuardStart(grid);
    return simulateGuard(grid, obstructionData, guardStart).getLeft();
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    var obstructionData = buildObstructionData(grid);
    var guardStart = findGuardStart(grid);
    var visitedPositions = simulateGuard(grid, obstructionData, guardStart).getMiddle();
    visitedPositions.remove(guardStart.getLeft());
    return (int) visitedPositions.stream().filter(pos -> simulateGuardWithObstruction(grid, obstructionData, guardStart, pos)).count();
  }

  public Pair<Loc, Direction> findGuardStart(InfiniteGrid grid) {
    return grid.grid.entrySet().stream()
        .filter(e -> "^v<>".indexOf(e.getValue()) != -1)
        .map(e -> new Pair<>(e.getKey(), Direction.caretToDirection(e.getValue())))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Guard starting position not found."));
  }

  public record ObstructionData(Set<Loc> obstruction, Map<Long, NavigableSet<Long>> obstructionXInRow, Map<Long, NavigableSet<Long>> obstructionYInCol) {}

  public ObstructionData buildObstructionData(InfiniteGrid grid) {
    Set<Loc> obstruction = new HashSet<>();
    Map<Long, NavigableSet<Long>> obstructionXInRow = new HashMap<>();
    Map<Long, NavigableSet<Long>> obstructionYInCol = new HashMap<>();

    grid.grid.forEach((loc, c) -> {
      if (c == '#') {
        obstruction.add(loc);
        obstructionXInRow.computeIfAbsent(loc.y, k -> new TreeSet<>()).add(loc.x);
        obstructionYInCol.computeIfAbsent(loc.x, k -> new TreeSet<>()).add(loc.y);
      }
    });

    return new ObstructionData(obstruction, obstructionXInRow, obstructionYInCol);
  }

  public Triple<Integer, Set<Loc>, Boolean> simulateGuard(InfiniteGrid grid, ObstructionData obstructionData, Pair<Loc, Direction> guardStart) {
    return simulate(grid, obstructionData, guardStart, null);
  }

  public boolean simulateGuardWithObstruction(InfiniteGrid grid, ObstructionData obstructionData, Pair<Loc, Direction> guardStart, Loc obstructionPos) {
    Set<Loc> obstruction = new HashSet<>(obstructionData.obstruction());
    if (obstruction.contains(obstructionPos)) return false;
    obstruction.add(obstructionPos);

    Map<Long, NavigableSet<Long>> obstructionXInRow = new HashMap<>();
    obstructionData.obstructionXInRow().forEach((k, v) -> obstructionXInRow.put(k, new TreeSet<>(v)));
    obstructionXInRow.computeIfAbsent(obstructionPos.y, k -> new TreeSet<>()).add(obstructionPos.x);

    Map<Long, NavigableSet<Long>> obstructionYInCol = new HashMap<>();
    obstructionData.obstructionYInCol().forEach((k, v) -> obstructionYInCol.put(k, new TreeSet<>(v)));
    obstructionYInCol.computeIfAbsent(obstructionPos.x, k -> new TreeSet<>()).add(obstructionPos.y);

    return simulate(grid, new ObstructionData(obstruction, obstructionXInRow, obstructionYInCol), guardStart, obstructionPos).getRight();
  }

  private Triple<Integer, Set<Loc>, Boolean> simulate(InfiniteGrid grid, ObstructionData obstructionData, Pair<Loc, Direction> guardStart, Loc obstructionPos) {
    long minX = grid.minX(), maxX = grid.maxX(), minY = grid.minY(), maxY = grid.maxY();
    Loc pos = guardStart.a();
    Direction facing = guardStart.b();
    long x = pos.x, y = pos.y;
    Set<Loc> visitedPositions = new HashSet<>();
    Set<Triple<Long, Long, Direction>> visitedStates = new HashSet<>();
    visitedPositions.add(new Loc(x, y));
    visitedStates.add(Triple.of(x, y, facing));
    boolean looped = false;

    while (true) {
      long stepsToObst = MAX_VALUE, stepsToEdge = MAX_VALUE;
      var obstaclesAhead = getObstaclesAhead(obstructionData, facing, x, y);
      if (!obstaclesAhead.isEmpty()) stepsToObst = getStepsToObst(obstaclesAhead, facing, x, y);
      stepsToEdge = getStepsToEdge(facing, x, y, minX, maxX, minY, maxY);

      long nSteps = Math.min(stepsToObst, stepsToEdge);
      if (nSteps <= 0) {
        facing = facing.turn(true);
        Triple<Long, Long, Direction> state = Triple.of(x, y, facing);
        if (visitedStates.contains(state)) {
          looped = true;
          break;
        }
        visitedStates.add(state);
        Loc nextPos = facing.move(new Loc(x, y));
        if (nextPos.x < minX || nextPos.x > maxX || nextPos.y < minY || nextPos.y > maxY) break;
        continue;
      } else {
        for (long i = 0; i < nSteps; i++) {
          Loc l = new Loc(x, y).move(facing);
          x = l.x;
          y = l.y;
          Loc newPos = new Loc(x, y);
          Triple<Long, Long, Direction> state = Triple.of(x, y, facing);
          if (visitedStates.contains(state)) {
            looped = true;
            break;
          }
          visitedStates.add(state);
          if (x >= minX && x <= maxX && y >= minY && y <= maxY) visitedPositions.add(newPos);
          else break;
        }
        if (looped) break;
        Loc l = new Loc(x, y).move(facing);
        long nx = l.x, ny = l.y;
        if (nx < minX || nx > maxX || ny < minY || ny > maxY) break;
        Loc nextPos = new Loc(nx, ny);
        if (obstructionData.obstruction().contains(nextPos)) {
          facing = facing.turn(true);
          Triple<Long, Long, Direction> state = Triple.of(x, y, facing);
          if (visitedStates.contains(state)) {
            looped = true;
            break;
          }
          visitedStates.add(state);
        }
      }
    }
    return Triple.of(visitedPositions.size(), visitedPositions, looped);
  }

  private Set<Long> getObstaclesAhead(ObstructionData obstructionData, Direction facing, long x, long y) {
    return switch (facing) {
      case NORTH -> obstructionData.obstructionYInCol().getOrDefault(x, new TreeSet<>()).headSet(y, false);
      case SOUTH -> obstructionData.obstructionYInCol().getOrDefault(x, new TreeSet<>()).tailSet(y + 1);
      case WEST -> obstructionData.obstructionXInRow().getOrDefault(y, new TreeSet<>()).headSet(x, false);
      case EAST -> obstructionData.obstructionXInRow().getOrDefault(y, new TreeSet<>()).tailSet(x + 1);
      default -> new TreeSet<>();
    };
  }

  private long getStepsToObst(Set<Long> obstaclesAhead, Direction facing, long x, long y) {
    return switch (facing) {
      case NORTH -> y - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case SOUTH -> ((TreeSet<Long>) obstaclesAhead).first() - y - 1;
      case WEST -> x - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case EAST -> ((TreeSet<Long>) obstaclesAhead).first() - x - 1;
      default -> MAX_VALUE;
    };
  }

  private long getStepsToEdge(Direction facing, long x, long y, long minX, long maxX, long minY, long maxY) {
    return switch (facing) {
      case NORTH -> y - minY;
      case SOUTH -> maxY - y;
      case WEST -> x - minX;
      case EAST -> maxX - x;
      default -> MAX_VALUE;
    };
  }
}
