package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day4 extends Day2022 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) throws IOException {
    new Day4().printParts();
  }

  public record Assignment(long aStart, long aEnd, long bStart, long bEnd) {
    public boolean contained() {
      return (aStart >= bStart && aEnd <= bEnd) || (bStart >= aStart && bEnd <= aEnd);
    }

    public boolean overlap() {
      return aStart <= bEnd && aEnd >= bStart;
    }
  }

  @Override
  public Object part1() {
    return input().filter(Assignment::contained).count();
  }

  private Stream<Assignment> input() {
    return dayStream().map(String::trim).map(s -> readString(s, "%n-%n,%n-%n", Assignment.class));
  }

  @Override
  public Object part2() {
    return input().filter(Assignment::overlap).count();
  }
}
