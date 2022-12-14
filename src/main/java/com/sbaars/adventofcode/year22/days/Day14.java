package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.util.stream.IntStream.rangeClosed;

public class Day14 extends Day2022 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  public record Pos(long x, long y){}

  @Override
  public Object part1() {
    return amountOfSand(true);
  }

  public int amountOfSand (boolean part1) {
    List<List<Point>> in = dayStream().map(s -> Arrays.asList(s.split(" -> ")).stream().map(s2 -> readString(s2, "%n,%n", Pos.class)).map(p -> new Point(Math.toIntExact(p.x), Math.toIntExact(p.y))).toList()).collect(Collectors.toCollection(ArrayList::new));
    if(!part1) {
      int maxY = in.stream().flatMapToInt(e -> e.stream().mapToInt(f -> f.y)).max().getAsInt() + 2;
      in.add(List.of(new Point(0, maxY), new Point(999, maxY)));
    }
    InfiniteGrid g = new InfiniteGrid();
    for(List<Point> rock : in) {
      for(int i = 1; i<rock.size(); i++) {
        Point p1 = rock.get(i-1);
        Point p2 = rock.get(i);
        if(p1.x != p2.x) {
          int from = Math.min(p1.x, p2.x);
          int to = Math.max(p1.x, p2.x);
          rangeClosed(from, to).forEach(j -> g.set(new Point(j, p1.y), '#'));
        } else if(p1.y != p2.y) {
          int from = Math.min(p1.y, p2.y);
          int to = Math.max(p1.y, p2.y);
          rangeClosed(from, to).forEach(j -> g.set(new Point(p1.x, j), '#'));
        }
      }
    }
    Point sandOrigin = new Point(500, 0);
    Point fallingSand = sandOrigin;
    while(part1 ? fallingSand.y<950 : g.get(sandOrigin).isEmpty()) {
      Point moveTo = Direction.SOUTH.move(fallingSand);
      var destination = g.get(moveTo);
      if(destination.isEmpty()) {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHWEST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination.isEmpty()) {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHEAST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination.isEmpty()) {
        fallingSand = moveTo;
        continue;
      }
      g.set(fallingSand, 'o');
      fallingSand = sandOrigin;
    }
    return g.countChar('o');
  }

  @Override
  public Object part2() {
    return amountOfSand(false);
  }
}
