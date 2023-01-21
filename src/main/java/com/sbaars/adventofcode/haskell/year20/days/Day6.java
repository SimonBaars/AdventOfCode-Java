package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;

import java.util.Arrays;

public class Day6 extends HaskellDay2020 {
  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    return Arrays.stream(day().split("\n\n"))
        .map(i -> convert(i.split("\n")))
        .collect(haskellList());
  }

  @Override
  public Object part2() {
    return null;
  }
}
