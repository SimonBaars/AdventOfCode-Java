package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.util.AOCUtils;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;

import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day6 extends Day2023 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  public record Results(List<Integer> time, List<Integer> distance) {}

  @Override
  public Object part1() {
    Results in = readString(day().replaceAll(" +", " "), "Time: %li\nDistance: %li", " ", Results.class);
    return zip(in.time.stream(), in.distance.stream()).mapToLong(p -> calculateWays(p.a(), p.b())).reduce(1, (a, b) -> a * b);
  }

  public long calculateWays(int time, int recordDistance) {
    return time - Math.max(0, (int) Math.ceil((1 + Math.sqrt(1 + 8.0 * recordDistance)) / 2)) + 1;
  }

  @Override
  public Object part2() {
    return "";
  }
}
