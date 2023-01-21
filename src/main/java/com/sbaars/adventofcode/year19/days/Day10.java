package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.AtomicDouble;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year19.Day2019;

import java.util.Comparator;
import java.util.Set;

public class Day10 extends Day2019 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    Set<Loc> asteroids = new InfiniteGrid(dayGrid(), '.').grid.keySet();
    return getBase(asteroids).b();
  }

  private static Pair<Loc, Long> getBase(Set<Loc> asteroids) {
    return asteroids.stream()
        .map(a -> new Pair<>(a, asteroids.stream().filter(e -> !e.equals(a)).map(e -> e.rotation(a)).distinct().count()))
        .sorted(Comparator.comparingDouble(Pair::b))
        .reduce((a, b) -> b)
        .get();
  }

  @Override
  public Object part2() {
    Set<Loc> asteroids = new InfiniteGrid(dayGrid(), '.').grid.keySet();
    Loc base = getBase(asteroids).a();
    AtomicDouble rotation = new AtomicDouble(-1);
    for (int destroyed = 1; true; destroyed++) {
      rotation.set(asteroids.stream().mapToDouble(base::rotation).filter(e -> e > rotation.get()).min().orElseGet(() -> asteroids.stream().mapToDouble(base::rotation).min().getAsDouble()));
      if (destroyed == 200) {
        Loc l = asteroids.stream().filter(e -> base.rotation(e) == rotation.get()).reduce((a1, a2) -> a1.distanceDouble(base) < a2.distanceDouble(base) ? a1 : a2).get();
        return (l.x * 100) + l.y;
      }
    }
  }
}
