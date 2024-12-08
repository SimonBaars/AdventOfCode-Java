package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.map.ListMap.toListMapReversed;
import static com.sbaars.adventofcode.util.AoCUtils.allPairs;
import static com.sbaars.adventofcode.util.AoCUtils.gcd;
import static com.sbaars.adventofcode.util.AoCUtils.appendWhile;
import static java.lang.Character.isLetterOrDigit;
import java.util.stream.Stream;
import com.sbaars.adventofcode.common.map.ListMap;

public class Day8 extends Day2024 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    var in = new InfiniteGrid(dayGrid());
    return getAntennasByFrequency(in).stream().flatMapToObj((c, locs) ->
      allPairs(locs).flatMap(p -> Stream.of(
        p.a().translate(l -> l * 2).move(new Loc(-p.b().x, -p.b().y)),
        p.b().translate(l -> l * 2).move(new Loc(-p.a().x, -p.a().y))
      ).filter(in::contains))
    ).distinct().count();
  }

  @Override
  public Object part2() {
    var in = new InfiniteGrid(dayGrid());
    return getAntennasByFrequency(in).stream().flatMapToObj((c, locs) -> {
      return allPairs(locs).flatMap(p -> {
        long dx = p.b().x - p.a().x;
        long dy = p.b().y - p.a().y;
        long gcd = gcd(Math.abs(dx), Math.abs(dy));
        long stepX = dx / gcd;
        long stepY = dy / gcd;
        return Stream.concat(
          appendWhile(l -> l.move(stepX, stepY), in::contains, p.a()),
          appendWhile(l -> l.move(-stepX, -stepY), in::contains, p.a())
        );
      });
    }).distinct().count();
  }

  private ListMap<Character, Loc> getAntennasByFrequency(InfiniteGrid in) {
    return in.streamChars().filter((loc, ch) -> isLetterOrDigit(ch)).collect(toListMapReversed());
  }
}