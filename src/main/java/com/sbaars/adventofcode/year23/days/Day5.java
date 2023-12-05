package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day5 extends Day2023 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  public record Almanac(List<Integer> seeds, List<Mapping> maps) {}
  public record Mapping(String source, String dest, List<SeedNumbers> nums) {}
  public record SeedNumbers(long destinationStart, long sourceStart, long size) {}

  @Override
  public Object part1() {
    String[] day = day().split("\n\n");
    List<Integer> seeds = readString(day[0], "seeds: %ln", " ", ArrayList.class);
    List<Mapping> mappings = Arrays.stream(Arrays.copyOfRange(day, 1, day.length)).map(s -> readString(s, "%s-to-%s map:\n%l(%n %n %n)", "\n", Mapping.class, SeedNumbers.class)).toList();
    return new Almanac(seeds, mappings);
  }

  @Override
  public Object part2() {
    return "";
  }
}
