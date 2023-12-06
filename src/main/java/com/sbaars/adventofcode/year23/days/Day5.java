package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static com.sbaars.adventofcode.util.AOCUtils.pairs;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day5 extends Day2023 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  public record Mapping(String source, String dest, List<SeedNumbers> nums) {}
  public record SeedNumbers(long destinationStart, long sourceStart, long size) {}

  @Override
  public Object part1() {
    String[] day = day().split("\n\n");
    List<Long> seeds = getSeeds(day);
    List<Mapping> mappings = mappings(day);
    return seeds.stream().mapToLong(seed -> findLocations(day, mappings, seed)).min().getAsLong();
  }

  private ArrayList<Long> getSeeds(String[] day) {
    return readString(day[0], "seeds: %ln", " ", ArrayList.class);
  }

  private long findLocations(String[] day, List<Mapping> mappings, long seed) {
    return mappings
            .stream()
            .reduce(seed, (s, m) -> m.nums.stream().map(n -> new Loc(n.sourceStart, n.sourceStart + n.size - 1).contains(s) ? s + (n.destinationStart - n.sourceStart) : -1).filter(n -> n != -1).findAny().orElse(s), Long::sum);
  }

  private static List<Mapping> mappings(String[] day) {
    return Arrays.stream(Arrays.copyOfRange(day, 1, day.length)).map(s -> readString(s, "%s-to-%s map:\n%l(%n %n %n)", "\n", Mapping.class, SeedNumbers.class)).toList();
  }

  @Override
  public Object part2() {
    String[] day = day().split("\n\n");
    List<Long> seeds = getSeeds(day);
    List<Mapping> mappings = mappings(day);
    return pairs(seeds).flatMap(p -> LongStream.range(p.a(), p.a() + p.b()).boxed()).parallel().mapToLong(s -> findLocations(day, mappings, s)).min().getAsLong();
  }
}
