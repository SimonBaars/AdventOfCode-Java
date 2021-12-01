package com.sbaars.adventofcode.haskell.year21.days;

import com.sbaars.adventofcode.haskell.year21.HaskellDay2021;

public class Day1 extends HaskellDay2021 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    return dayNumberStream().mapToObj(Long::toString).collect(haskellList());
  }

  @Override
  public Object part2() {
    return null;
  }
}
