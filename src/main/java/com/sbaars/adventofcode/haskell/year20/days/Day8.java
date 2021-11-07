package com.sbaars.adventofcode.haskell.year20.days;

import static java.util.Arrays.stream;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;
import com.sbaars.adventofcode.year20.gamepad.Gamepad;

public class Day8 extends HaskellDay2020 {
  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    return stream(new Gamepad(dayStream()).getInstructions())
        .map(i -> tup(i.operation(), i.number()))
        .collect(haskellList());
  }

  @Override
  public Object part2() {
    return null;
  }
}
