package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.List;
import java.util.stream.IntStream;

import static com.sbaars.adventofcode.common.Direction.fourDirections;
import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;
import static java.util.Arrays.stream;

public class Day15 extends Day2022 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  @Override
  public Object part1() {
    List<Range> posList = input();
    InfiniteGrid g = posList.stream().flatMap(Range::flatten).collect(toInfiniteGrid('X'));
    return IntStream.range(-1000000, 5000000) // These values were found by trial-and-error
            .mapToObj(i -> new Loc(i, 2000000))
            .filter(l -> posList.stream().anyMatch(p -> l.distance(p.start) <= p.distance() && g.get(l).isEmpty()))
            .count();
  }

  @Override
  public Object part2() {
    List<Range> posList = input();
    Range target = new Range(new Loc(0, 0), new Loc(4000000, 4000000));
    return input().stream()
            .flatMap(p -> stream(fourDirections()).flatMap(d -> d.move(p.start, p.distance() + 1).walk(d.turnSteps(3), p.distance() + 1)))
            .filter(target::inRange)
            .filter(l -> posList.stream().allMatch(p -> l.distance(p.start) > p.distance()))
            .mapToLong(l -> l.x * 4000000 + l.y)
            .findAny()
            .getAsLong();
  }

  private List<Range> input() {
    return dayStream().map(s -> readString(s, "Sensor at x=%n, y=%n: closest beacon is at x=%n, y=%n", Range.class)).toList();
  }
}
