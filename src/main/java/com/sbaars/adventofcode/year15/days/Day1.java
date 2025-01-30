package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;

import static com.sbaars.adventofcode.util.AoCUtils.findReduce;
import static com.sbaars.adventofcode.util.AoCUtils.zipWithIndex;

public class Day1 extends Day2015 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .flatMapToInt(String::chars)
        .map(c -> c == '(' ? 1 : -1)
        .sum();
  }

  @Override
  public Object part2() {
    return findReduce(
        zipWithIndex(dayStream().flatMapToInt(String::chars).boxed()),
        0,
        (c, acc) -> acc + (c.e() == '(' ? 1 : -1),
        acc -> acc < 0
    ).i() + 1;
  }
}
