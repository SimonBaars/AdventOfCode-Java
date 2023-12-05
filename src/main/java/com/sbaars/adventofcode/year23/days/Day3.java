package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.year23.Day2023;

public class Day3 extends Day2023 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    var grid = new InfiniteGrid(dayGrid());
    return grid.findGroups(Character::isDigit, true)
            .filter((locs, s) -> grid.findAround(c -> c != '.' && !Character.isDigit(c), locs.stream(), true).findAny().isPresent())
            .mapToLong((locs, s) -> Long.parseLong(s))
            .sum();
  }

  @Override
  public Object part2() {
    return "";
  }
}
