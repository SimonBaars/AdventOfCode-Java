package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.Tree;
import com.sbaars.adventofcode.year19.Day2019;

import static com.sbaars.adventofcode.common.Tree.toTree;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day6 extends Day2019 implements HasRecursion {
  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    return input().stream().mapToInt(n -> n.depth).sum();
  }

  @Override
  public Object part2() {
    Tree<String> in = input();
    return in.distance(in.get("YOU"), in.get("SAN")) - 2;
  }

  private Tree<String> input() {
    return dayStream().map(s -> (Pair<String, String>) readString(s, "%s)%s", Pair.class)).collect(toTree());
  }
}
