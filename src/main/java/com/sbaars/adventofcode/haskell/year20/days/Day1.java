package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;

public class Day1 extends HaskellDay2020 {
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
