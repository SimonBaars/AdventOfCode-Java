package com.sbaars.adventofcode.haskell.year20.days;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;
import java.util.Arrays;

public class Day3 extends HaskellDay2020 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    return Arrays.stream(dayGrid()).map(Arrays::toString)
        .map(s -> s.replace('.', '0'))
        .map(s -> s.replace('#', '1'))
        .collect(haskellList());
  }

  @Override
  public Object part2() {
    return null;
  }
}
