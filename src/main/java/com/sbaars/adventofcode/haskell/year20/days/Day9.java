package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;

public class Day9 extends HaskellDay2020 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
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
