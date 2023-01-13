package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.common.map.CountMap;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;
import static com.sbaars.adventofcode.common.map.ListMap.toListMap;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.IntStream.range;

public class Day6 extends Day2018 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    List<Loc> coords = dayStream().map(s -> readString(s, "%n, %n", Loc.class)).toList();
    InfiniteGrid g = zip(coords.stream(), range(0, coords.size()).mapToObj(c -> (char)c)).collect(toInfiniteGrid());
    long minX = g.minX(), minY = g.minY(), maxX = g.maxX(), maxY = g.maxY();
    InfiniteGrid areas = new InfiniteGrid(g);
    CountMap<Character> nChar = new CountMap<>();
    g.grid.values().forEach(nChar::increment);
    Set<Loc> infinite = new HashSet<>();
    new Range(minX, minY, maxX, maxY).stream().filter(l -> !g.contains(l) && !infinite.contains(l)).forEach(l -> {
      ListMap<Long, Loc> distance = g.stream().collect(toListMap(l::distance, loc -> loc));
      long min = distance.keySet().stream().mapToLong(e -> e).min().getAsLong();
      if(distance.get(min).size() == 1) {
        Loc loc = distance.get(min).get(0);
        Character c = g.get(loc).get();
        if(l.x == minX || l.x == maxX || l.y == minY || l.y == maxY) { // Must be infinite
          areas.removeIf((a, b) -> c.equals(b));
          nChar.remove(c);
          infinite.add(loc);
        } else {
          areas.set(l, c);
          nChar.increment(c);
        }
      }
    });
    return nChar.max();
  }

  @Override
  public Object part2() {
    List<Loc> coords = dayStream().map(s -> readString(s, "%n, %n", Loc.class)).toList();
    InfiniteGrid g = zip(coords.stream(), range(0, coords.size()).mapToObj(c -> (char)c)).collect(toInfiniteGrid());
    long minX = g.minX(), minY = g.minY(), maxX = g.maxX(), maxY = g.maxY();
    CountMap<Character> nChar = new CountMap<>();
    g.grid.values().forEach(nChar::increment);
    return new Range(minX, minY, maxX, maxY).stream().filter(l -> g.stream().mapToLong(l::distance).sum() < 10_000L).count();
  }
}
