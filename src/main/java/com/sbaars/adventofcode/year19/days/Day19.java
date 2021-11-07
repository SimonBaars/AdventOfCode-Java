package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;

public class Day19 extends Day2019 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  @Override
  public Object part1() {
    long res = 0;
    for (int x = 0; x < 50; x++)
      for (int y = 0; y < 50; y++)
        res += new IntcodeComputer(19).run(x, y);
    return res;
  }

  @Override
  public Object part2() {
    int x = 500, y = 0;
    while (true) {
      if (beam(x, y)) {
        if (beam(x - 99, y + 99)) return 10000 * (x - 99) + y;
        else x++;
      } else y++;
    }
  }

  private boolean beam(int x, int y) {
    return new IntcodeComputer(19).run(x, y) == 1L;
  }
}
