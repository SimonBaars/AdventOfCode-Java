package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.location.MutableLoc;
import com.sbaars.adventofcode.year15.Day2015;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.util.AoCUtils.zipWithIndex;

public class Day3 extends Day2015 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    MutableLoc loc = new MutableLoc();
    return day()
        .chars()
        .mapToObj(c -> charToDir((char) c))
        .map(d -> loc.set(d.move(loc.get())))
        .distinct()
        .count();
  }

  @Override
  public Object part2() {
    MutableLoc santa = new MutableLoc();
    MutableLoc robo = new MutableLoc();
    return zipWithIndex(day().chars().mapToObj(c -> charToDir((char) c)))
        .map(d -> d.i() % 2 == 0 ? santa.set(d.e().move(santa.get())) : robo.set(d.e().move(robo.get())))
        .distinct()
        .count();
  }

  public Direction charToDir(char c) {
    return switch (c) {
      case '^' -> NORTH;
      case '>' -> EAST;
      case 'v' -> SOUTH;
      case '<' -> WEST;
      default -> throw new IllegalStateException("Unknown char: " + c);
    };
  }
}
