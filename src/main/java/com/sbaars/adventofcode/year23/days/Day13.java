package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;
import java.util.Map;

import static com.sbaars.adventofcode.util.AOCUtils.connectedPairs;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static java.util.Arrays.stream;
import static java.util.stream.LongStream.range;

public class Day13 extends Day2023 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  @Override
  public Object part1() {
    return solve(0);
  }

  @Override
  public Object part2() {
    return solve(1);
  }

  private long solve(long errorsAllowed) {
    return stream(day().split("\n\n")).map(InfiniteGrid::new).mapToLong(g -> findReflection(g.columnValues(), errorsAllowed) + findReflection(g.rowValues(), errorsAllowed) * 100).sum();
  }

  private long findReflection(Map<Long, List<Character>> chars, long errorsAllowed) {
    return connectedPairs(chars.entrySet().stream())
        .filter(r -> zip(r.a().getValue().stream(), r.b().getValue().stream()).filter(c -> c.a() != c.b()).count() +
            range(0, r.a().getKey())
                .mapToObj(i -> new Pair<>(r.a().getKey() - i - 1, r.b().getKey() + i + 1))
                .filter(p -> chars.containsKey(p.a()) && chars.containsKey(p.b()) && !chars.get(p.a()).equals(chars.get(p.b())))
                .count() == errorsAllowed
        )
        .peek(System.out::println)
        .mapToLong(p -> p.b().getKey())
        .max()
        .orElse(0);
  }
}
