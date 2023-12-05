package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.year23.Day2023;
import java.util.HashSet;

import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Direction.eight;
import static java.lang.Long.parseLong;

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
            .mapToLong((locs, s) -> parseLong(s))
            .sum();
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    var groups = grid.findGroups(Character::isDigit, true).filter((locs, s) -> !s.isEmpty()).mapToObj((a, b) -> new Pair<>(new HashSet<>(a), parseLong(b))).toList();
    return grid.findAll('*')
            .map(l -> eight().map(d -> d.move(l)).flatMap(l2 -> groups.stream().filter(g -> g.a().contains(l2))).distinct().toList())
            .filter(p -> p.size() == 2)
            .mapToLong(p -> p.get(0).b() * p.get(1).b())
            .sum();
  }
}
