package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Day6 extends Day2023 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  public record Results(List<Integer> time, List<Integer> distance) {
  }

  @Override
  public Object part1() {
    Results in = readString(day().replaceAll(" +", " "), "Time: %li\nDistance: %li", " ", Results.class);
    return range(0, in.time.size())
        .mapToLong(i -> countWays(in.time.get(i), in.distance.get(i)))
        .reduce(1, (a, b) -> a * b);
  }

  private static long countWays(int time, long distance) {
    return rangeClosed(0, time)
        .filter(holdTime -> (long) holdTime * (time - holdTime) > distance)
        .count();
  }

  public record SingleRace(int time, long distance) {
  }

  @Override
  public Object part2() {
    SingleRace in = readString(day().replaceAll(" ", ""), "Time:%i\nDistance:%n", SingleRace.class);
    return countWays(in.time, in.distance);
  }
}
