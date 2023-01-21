package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.CircularList;
import com.sbaars.adventofcode.year22.Day2022;

public class Day20 extends Day2022 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  @Override
  public Object part1() {
    return solve(1, 1);
  }

  @Override
  public Object part2() {
    return solve(811589153L, 10);
  }

  public long solve(long multiplier, int times) {
    CircularList in = new CircularList(dayNumberStream().map(l -> l * multiplier).toArray());
    for (int j = 0; j < times; j++) {
      for (int i = 0; i < in.values.size(); i++) {
        var toMove = in.values.get(i);
        var moveTo = in.move(toMove, toMove.value);
        in.insertAfter(toMove, moveTo);
      }
    }
    in.setCurrent(in.values.stream().filter(n -> n.value == 0).findAny().get());
    long[] nums = in.next(3000);
    return nums[1000 - 1] + nums[2000 - 1] + nums[3000 - 1];
  }
}
