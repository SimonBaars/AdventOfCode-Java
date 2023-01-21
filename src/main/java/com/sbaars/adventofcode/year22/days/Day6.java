package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.stream.IntStream;

public class Day6 extends Day2022 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) throws IOException {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    return calculateAnswer(4);
  }

  private int calculateAnswer(int size) {
    String s = day();
    return IntStream.range(0, s.length()).filter(i -> s.substring(i, i + size).chars().distinct().count() == size).findFirst().getAsInt() + size;
  }

  @Override
  public Object part2() {
    return calculateAnswer(14);
  }
}
