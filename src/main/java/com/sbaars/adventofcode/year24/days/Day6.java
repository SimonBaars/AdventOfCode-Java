package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.grid.Walker;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Direction.NORTH;
import static java.lang.Long.MAX_VALUE;
import com.sbaars.adventofcode.common.Pair;

import java.util.*;
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
    Loc l = grid.findAll('^').findFirst().get();
    return path(grid, new Walker(l, NORTH)).size();
  }

  private Set<Loc> path(InfiniteGrid g, Walker walker) {
    Set<Loc> visited = new HashSet<>();
    while (true) {
      var c = g.get(walker.loc());
      if (!g.contains(walker.loc()))
        break;
      else if (c.get() == '#')
        walker = walker.walkBack().turn();
      else {
        visited.add(walker.loc());
        walker = walker.walk();
      }
    }
    return visited;
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    var obstructionData = buildObstructionData(grid);
    var guardStart = findGuardStart(grid);
    var visitedPositions = simulateGuard(grid, obstructionData, guardStart).getMiddle();
    visitedPositions.remove(guardStart.getLeft());
    return (int) visitedPositions.stream()
        .filter(pos -> simulateGuardWithObstruction(grid, obstructionData, guardStart, pos)).count();
  }

  public Pair<Loc, Direction> findGuardStart(InfiniteGrid grid) {
    return grid.grid.entrySet().stream()
        .filter(e -> "^v<>".indexOf(e.getValue()) != -1)
        .map(e -> Pair.of(e.getKey(), Direction.caretToDirection(e.getValue())))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Guard starting position not found."));
  }

  public record ObstructionData(Set<Loc> obstruction, Map<Long, NavigableSet<Long>> obstructionXInRow,
      Map<Long, NavigableSet<Long>> obstructionYInCol) {
  }

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

  public Triple<Integer, Set<Loc>, Boolean> simulateGuard(InfiniteGrid grid, ObstructionData obstructionData,
      Pair<Loc, Direction> guardStart) {
    return simulate(grid, obstructionData, guardStart, null);
  }

  public boolean simulateGuardWithObstruction(InfiniteGrid grid, ObstructionData obstructionData,
      Pair<Loc, Direction> guardStart, Loc obstructionPos) {
    Set<Loc> obstruction = new HashSet<>(obstructionData.obstruction());
    if (obstruction.contains(obstructionPos))
      return false;
    obstruction.add(obstructionPos);

    Map<Long, NavigableSet<Long>> obstructionXInRow = new HashMap<>();
    obstructionData.obstructionXInRow().forEach((k, v) -> obstructionXInRow.put(k, new TreeSet<>(v)));
    obstructionXInRow.computeIfAbsent(obstructionPos.y, k -> new TreeSet<>()).add(obstructionPos.x);

    Map<Long, NavigableSet<Long>> obstructionYInCol = new HashMap<>();
    obstructionData.obstructionYInCol().forEach((k, v) -> obstructionYInCol.put(k, new TreeSet<>(v)));
    obstructionYInCol.computeIfAbsent(obstructionPos.x, k -> new TreeSet<>()).add(obstructionPos.y);

    return simulate(grid, new ObstructionData(obstruction, obstructionXInRow, obstructionYInCol), guardStart,
        obstructionPos).getRight();
  }

  private Triple<Integer, Set<Loc>, Boolean> simulate(InfiniteGrid grid, ObstructionData obstructionData,
      Pair<Loc, Direction> guardStart, Loc obstructionPos) {
    long minX = grid.minX(), maxX = grid.maxX(), minY = grid.minY(), maxY = grid.maxY();
    Walker walker = new Walker(guardStart.getLeft(), guardStart.getRight());
    Set<Loc> visitedPositions = new HashSet<>();
    Set<Pair<Loc, Direction>> visitedStates = new HashSet<>();
    visitedPositions.add(walker.loc());
    visitedStates.add(Pair.of(walker.loc(), walker.direction()));
    boolean looped = false;

    for (;;) {
      long stepsToObst = MAX_VALUE, stepsToEdge = MAX_VALUE;
      var obstaclesAhead = getObstaclesAhead(obstructionData, walker.direction(), walker.loc());
      if (!obstaclesAhead.isEmpty())
        stepsToObst = getStepsToObst(obstaclesAhead, walker.direction(), walker.loc());
      stepsToEdge = getStepsToEdge(walker.direction(), walker.loc(), minX, maxX, minY, maxY);

      long nSteps = Math.min(stepsToObst, stepsToEdge);
      if (nSteps <= 0) {
        walker = walker.turn(true);
        Pair<Loc, Direction> state = Pair.of(walker.loc(), walker.direction());
        if (visitedStates.contains(state)) {
          looped = true;
          break;
        }
        visitedStates.add(state);
        Loc nextPos = walker.direction().move(walker.loc());
        if (!grid.contains(nextPos))
          break;
      } else {
        for (long i = 0; i < nSteps; i++) {
          walker = walker.walk();
          Pair<Loc, Direction> state = Pair.of(walker.loc(), walker.direction());
          if (visitedStates.contains(state)) {
            looped = true;
            break;
          }
          visitedStates.add(state);
          if (grid.contains(walker.loc()))
            visitedPositions.add(walker.loc());
          else
            break;
        }
        if (looped)
          break;
        Loc nextPos = walker.direction().move(walker.loc());
        if (!grid.contains(nextPos))
          break;
        if (obstructionData.obstruction().contains(nextPos)) {
          walker = walker.turn(true);
          Pair<Loc, Direction> state = Pair.of(walker.loc(), walker.direction());
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

  private Set<Long> getObstaclesAhead(ObstructionData obstructionData, Direction facing, Loc pos) {
    return switch (facing) {
      case NORTH -> obstructionData.obstructionYInCol().getOrDefault(pos.x, new TreeSet<>()).headSet(pos.y, false);
      case SOUTH -> obstructionData.obstructionYInCol().getOrDefault(pos.x, new TreeSet<>()).tailSet(pos.y + 1);
      case WEST -> obstructionData.obstructionXInRow().getOrDefault(pos.y, new TreeSet<>()).headSet(pos.x, false);
      case EAST -> obstructionData.obstructionXInRow().getOrDefault(pos.y, new TreeSet<>()).tailSet(pos.x + 1);
      default -> new TreeSet<>();
    };
  }

  private long getStepsToObst(Set<Long> obstaclesAhead, Direction facing, Loc pos) {
    return switch (facing) {
      case NORTH -> pos.y - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case SOUTH -> ((TreeSet<Long>) obstaclesAhead).first() - pos.y - 1;
      case WEST -> pos.x - ((TreeSet<Long>) obstaclesAhead).last() - 1;
      case EAST -> ((TreeSet<Long>) obstaclesAhead).first() - pos.x - 1;
      default -> MAX_VALUE;
    };
  }

  private long getStepsToEdge(Direction facing, Loc pos, long minX, long maxX, long minY, long maxY) {
    return switch (facing) {
      case NORTH -> pos.y - minY;
      case SOUTH -> maxY - pos.y;
      case WEST -> pos.x - minX;
      case EAST -> maxX - pos.x;
      default -> MAX_VALUE;
    };
  }
}
