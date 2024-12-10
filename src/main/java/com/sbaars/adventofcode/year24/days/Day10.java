package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static com.google.mu.util.stream.BiStream.zip;
import static com.sbaars.adventofcode.util.AoCUtils.transformStream;
import static com.sbaars.adventofcode.util.AoCUtils.split;

import java.util.stream.Stream;

public class Day10 extends Day2024 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    return solve().distinct().count();
  }

  @Override
  public Object part2() {
    return solve().count();
  }

  private Stream<Pair<Loc, Loc>> solve() {
    InfiniteGrid g = new InfiniteGrid(dayGrid());
    return transformStream(
      g.findAll('0').map(l -> new Pair<>(l, l)),
      s -> split(s, (s1, s2) -> zip(s1, g.around((c1, c2) -> Character.getNumericValue(c1) + 1 == Character.getNumericValue(c2), s2.map(p -> p.b()), false)).flatMapToObj((a, b) -> b.map(l -> new Pair<>(a.a(), l)))),
      p -> g.getOptimistic(p.b()) == '9'
    );
  }
}
