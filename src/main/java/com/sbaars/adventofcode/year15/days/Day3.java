package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.MutableLoc;
import com.sbaars.adventofcode.year15.Day2015;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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
    return countVisitedHouses(dayStream()
        .flatMapToInt(String::chars)
        .mapToObj(c -> charToDir((char) c)));
  }

  @Override
  public Object part2() {
    return countVisitedHousesWithRobo(dayStream()
        .flatMapToInt(String::chars)
        .mapToObj(c -> charToDir((char) c)));
  }

  private long countVisitedHouses(Stream<Direction> directions) {
    MutableLoc loc = new MutableLoc();
    Set<Loc> visited = new HashSet<>();
    visited.add(loc.get());
    directions.forEach(d -> visited.add(loc.set(d.move(loc.get()))));
    return visited.size();
  }

  private long countVisitedHousesWithRobo(Stream<Direction> directions) {
    MutableLoc santa = new MutableLoc();
    MutableLoc robo = new MutableLoc();
    Set<Loc> visited = new HashSet<>();
    visited.add(santa.get());
    
    zipWithIndex(directions)
        .forEach(d -> {
            var current = d.i() % 2 == 0 ? santa : robo;
            visited.add(current.set(d.e().move(current.get())));
        });
    return visited.size();
  }

  private static Direction charToDir(char c) {
    return switch (c) {
      case '^' -> NORTH;
      case '>' -> EAST;
      case 'v' -> SOUTH;
      case '<' -> WEST;
      default -> throw new IllegalStateException("Unknown char: " + c);
    };
  }
}
