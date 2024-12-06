package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static java.lang.Long.MAX_VALUE;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

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
        .map(e -> Pair.of(e.getKey(), Direction.caretToDirection(e.getValue())))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Guard starting position not found."));
  }

  public record ObstructionData(Set<Loc> obstruction, Map<Long, NavigableSet<Long>> obstructionXInRow, Map<Long, NavigableSet<Long>> obstructionYInCol) {}

  public ObstructionData buildObstructionData(InfiniteGrid grid) {
    var obstruction = new HashSet<Loc>();
    var obstructionXInRow = new HashMap<Long, NavigableSet<Long>>();
    var obstructionYInCol = new HashMap<Long, NavigableSet<Long>>();

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
    var obstruction = new HashSet<>(obstructionData.obstruction());
    if (obstruction.contains(obstructionPos)) return false;
    obstruction.add(obstructionPos);

    var obstructionXInRow = new HashMap<Long, NavigableSet<Long>>();
    obstructionData.obstructionXInRow().forEach((k, v) -> obstructionXInRow.put(k, new TreeSet<>(v)));
    obstructionXInRow.computeIfAbsent(obstructionPos.y, k -> new TreeSet<>()).add(obstructionPos.x);

    var obstructionYInCol = new HashMap<Long, NavigableSet<Long>>();
    obstructionData.obstructionYInCol().forEach((k, v) -> obstructionYInCol.put(k, new TreeSet<>(v)));
    obstructionYInCol.computeIfAbsent(obstructionPos.x, k -> new TreeSet<>()).add(obstructionPos.y);

    return simulate(grid, new ObstructionData(obstruction, obstructionXInRow, obstructionYInCol), guardStart, obstructionPos).getRight();
  }

  private Triple<Integer, Set<Loc>, Boolean> simulate(InfiniteGrid grid, ObstructionData obstructionData, Pair<Loc, Direction> guardStart, Loc obstructionPos) {
    long minX = grid.minX(), maxX = grid.maxX(), minY = grid.minY(), maxY = grid.maxY();
    Loc pos = guardStart.getLeft();
    Direction facing = guardStart.getRight();
    var visitedPositions = new HashSet<Loc>();
    var visitedStates = new HashSet<Pair<Loc, Direction>>();
    visitedPositions.add(pos);
    visitedStates.add(Pair.of(pos, facing));
    boolean looped = false;

    while (true) {
      long stepsToObst = MAX_VALUE, stepsToEdge = MAX_VALUE;
      var obstaclesAhead = getObstaclesAhead(obstructionData, facing, pos);
      if (!obstaclesAhead.isEmpty()) stepsToObst = getStepsToObst(obstaclesAhead, facing, pos);
      stepsToEdge = getStepsToEdge(facing, pos, minX, maxX, minY, maxY);

      long nSteps = Math.min(stepsToObst, stepsToEdge);
      if (nSteps <= 0) {
        facing = facing.turn();
        var state = Pair.of(pos, facing);
        if (visitedStates.contains(state)) {
          looped = true;
          break;
        }
        visitedStates.add(state);
        pos = facing.move(pos);
        if (pos.x < minX || pos.x > maxX || pos.y < minY || pos.y > maxY) break;
        continue;
      } else {
        for (long i = 0; i < nSteps; i++) {
          pos = pos.move(facing);
          var state = Pair.of(pos, facing);
          if (visitedStates.contains(state)) {
            looped = true;
            break;
          }
          visitedStates.add(state);
          if (grid.contains(pos)) visitedPositions.add(pos);
          else break;
        }
        if (looped) break;
        pos = pos.move(facing);
        if (!grid.contains(pos)) break;
        if (obstructionData.obstruction().contains(pos)) {
          facing = facing.turn();
          var state = Pair.of(pos, facing);
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

  private Set<Long> getObstaclesAhead(ObstructionData obstructionData, Direction facing, Loc loc) {
    return switch (facing) {
      case NORTH -> obstructionData.obstructionYInCol().getOrDefault(loc.x, new TreeSet<>()).headSet(loc.y, false);
      case SOUTH -> obstructionData.obstructionYInCol().getOrDefault(loc.x, new TreeSet<>()).tailSet(loc.y + 1);
      case WEST -> obstructionData.obstructionXInRow().getOrDefault(loc.y, new TreeSet<>()).headSet(loc.x, false);
      case EAST -> obstructionData.obstructionXInRow().getOrDefault(loc.y, new TreeSet<>()).tailSet(loc.x + 1);
      default -> new TreeSet<>();
    };
  }

  private long getStepsToObst(Set<Long> obstaclesAhead, Direction facing, Loc loc) {
    return switch (facing) {
      case NORTH -> loc.y - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case SOUTH -> ((TreeSet<Long>) obstaclesAhead).first() - loc.y - 1;
      case WEST -> loc.x - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case EAST -> ((TreeSet<Long>) obstaclesAhead).first() - loc.x - 1;
      default -> MAX_VALUE;
    };
  }

  private long getStepsToEdge(Direction facing, Loc loc, long minX, long maxX, long minY, long maxY) {
    return switch (facing) {
      case NORTH -> loc.y - minY;
      case SOUTH -> maxY - loc.y;
      case WEST -> loc.x - minX;
      case EAST -> maxX - loc.x;
      default -> MAX_VALUE;
    };
  }
}
