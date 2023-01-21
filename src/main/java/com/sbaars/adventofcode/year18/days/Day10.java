package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.List;

import static com.sbaars.adventofcode.common.Pair.pair;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day10 extends Day2018 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    return findAnswer().a();
  }

  @Override
  public Object part2() {
    return findAnswer().b();
  }

  private Pair<String, Integer> findAnswer() {
    List<Range> list = dayStream().map(s -> readString(s, "position=<%n, %n> velocity=<%n, %n>", Range.class)).toList();
    Pair<Long, InfiniteGrid> smallest = new Pair<>(Long.MAX_VALUE, null);
    for (int i = 0; true; i++) {
      InfiniteGrid g = list.stream().map(Range::getStart).collect(toInfiniteGrid('#'));
      if (g.area() < smallest.a()) {
        smallest = new Pair<>(g.area(), g);
      } else return pair("\n" + smallest.b().toString(), i - 1);
      list = list.stream().map(r -> new Range(r.start.move(r.end), r.end)).toList();
    }
  }
}
