package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.map.ListMap.toListMap;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day22 extends Day2023 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  static int idCounter = 0;

  public record Brick(int id, List<Loc3D> cubes) {
    public Brick(long x1, long y1, long z1, long x2, long y2, long z2) {
      this(idCounter++, new Loc3D(x1, y1, z1).lineTo(new Loc3D(x2, y2, z2)));
    }
  }

  @Override
  public Object part1() {
    return solve(true);
  }

  @Override
  public Object part2() {
    return solve(false);
  }

  private long solve(boolean part1) {
    var in = dayStream().map(s -> readString(s, "%n,%n,%n~%n,%n,%n", Brick.class)).toList();
    var bricks = dropBricks(in);
    ListMap<Brick, Brick> supportedBy = findSupported(bricks, (b1, b2) -> new Pair<>(b2, b1));
    ListMap<Brick, Brick> supports = findSupported(bricks, Pair::new);
    var stream = bricks.parallelStream().filter(b -> part1 == (!supports.containsKey(b) || supports.get(b).stream().allMatch(b2 -> supportedBy.get(b2).size() > 1)));
    return part1 ? stream.count() : stream.mapToLong(b -> collectAll(b, bricks)).sum();
  }

  private static ListMap<Brick, Brick> findSupported(Set<Brick> bricks, BiFunction<Brick, Brick, Pair<Brick, Brick>> toPair) {
    return bricks
        .parallelStream()
        .flatMap(b ->
            bricks.stream()
                .filter(b2 -> !b.equals(b2))
                .filter(b2 -> b.cubes().stream().anyMatch(c -> b2.cubes.contains(c.move(0, 0, 1))))
                .map(b2 -> toPair.apply(b, b2))
        ).collect(toListMap(Pair::a, Pair::b));
  }

  private long collectAll(Brick brick, Set<Brick> parentBricks) {
    var bricksToFall = new HashSet<>(parentBricks);
    bricksToFall.remove(brick);
    return dropBricks(bricksToFall).stream().filter(b -> !parentBricks.contains(b)).count();
  }

  public Set<Brick> dropBricks(Collection<Brick> bricksToDrop) {
    var bricks = new Builder<Set<Brick>>(HashSet::new);
    bricks.getNew().addAll(new HashSet<>(bricksToDrop));
    while (!bricks.get().equals(bricks.getNew())) { // while loop till a fixed point is reached
      bricks.refresh();
      bricks.setNew(
          bricks.get().parallelStream().map(b -> b.cubes.stream().anyMatch(c -> c.z == 1 || bricks.get().stream().filter(b2 -> !b.equals(b2)).anyMatch(b2 -> b2.cubes.contains(c.move(0, 0, -1)))) ? b : new Brick(b.id, b.cubes.stream().map(c -> c.move(0, 0, -1)).toList())).collect(Collectors.toSet()));
    }
    return bricks.get();
  }
}
