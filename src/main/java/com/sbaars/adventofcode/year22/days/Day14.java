package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.MutableLoc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.util.AOCUtils;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;

public class Day14 extends Day2022 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  @Override
  public Object part1() {
    return amountOfSand(true);
  }

  @Override
  public Object part2() {
    return amountOfSand(false);
  }

  public int amountOfSand(boolean part1) {
    List<List<Loc>> in = dayStream()
            .map(s -> Arrays.asList(s.split(" -> "))
                    .stream()
                    .map(s2 -> readString(s2, "%n,%n", Loc.class))
                    .toList())
            .collect(Collectors.toCollection(ArrayList::new));
    if(!part1) {
      long maxY = in.stream().flatMapToLong(e -> e.stream().mapToLong(f -> f.y)).max().getAsLong() + 2;
      in.add(List.of(new Loc(0, maxY), new Loc(999, maxY)));
    }
    InfiniteGrid g = constructWalls(in);
    return simulateSand(part1, g);
  }

  private static InfiniteGrid constructWalls(List<List<Loc>> in) {
    return in.stream()
            .flatMap(AOCUtils::connectedPairs)
            .map(Range::new)
            .flatMap(Range::stream)
            .collect(toInfiniteGrid('#'));
  }

  private static int simulateSand(boolean part1, InfiniteGrid g) {
    Loc sandOrigin = new Loc(500, 0);
    MutableLoc fallingSand = new MutableLoc(sandOrigin);
    while(part1 ? fallingSand.get().y<950 : g.get(sandOrigin).isEmpty()) {
      Loc moveTo = Stream.of(SOUTH, SOUTHWEST, SOUTHEAST, CENTER)
              .map(d -> d.move(fallingSand.get()))
              .filter(p -> g.get(p).isEmpty())
              .findFirst()
              .get();
      if(moveTo.equals(fallingSand.get())) {
        g.set(fallingSand.get(), 'o');
        fallingSand.set(sandOrigin);
      } else {
        fallingSand.set(moveTo);
      }
    }
    return g.countChar('o');
  }
}
