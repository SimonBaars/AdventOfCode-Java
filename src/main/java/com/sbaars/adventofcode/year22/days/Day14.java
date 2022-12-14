package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day14 extends Day2022 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    Day d = new Day14();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
    //    System.in.read();
//        d.submitPart1();
        d.submitPart2();
  }

  public record Pos(long x, long y){}

  @Override
  public Object part1() {
    List<List<Point>> in = dayStream().map(s -> Arrays.asList(s.split(" -> ")).stream().map(s2 -> readString(s2, "%n,%n", Pos.class)).map(p -> new Point(Math.toIntExact(p.x), Math.toIntExact(p.y))).toList()).toList();
    CharGrid g = new CharGrid('.', 1000, 1000);
    for(List<Point> rock : in) {
      for(int i = 1; i<rock.size(); i++) {
        Point p1 = rock.get(i-1);
        Point p2 = rock.get(i);
        if(p1.x != p2.x) {
          int from = Math.min(p1.x, p2.x);
          int to = Math.max(p1.x, p2.x);
          for(int j = from; j<=to; j++){
            g.set(new Point(j, p1.y), '#');
          }
        } else if(p1.y != p2.y) {
          int from = Math.min(p1.y, p2.y);
          int to = Math.max(p1.y, p2.y);
          for(int j = from; j<=to; j++){
            g.set(new Point(p1.x, j), '#');
          }
        }
      }
    }
    Point sandOrigin = new Point(500, 0);
    g.set(sandOrigin, '+');
    Point fallingSand = sandOrigin;
//    g.set(fallingSand, 'o');
    while(fallingSand.y<950) {
      Point moveTo = Direction.SOUTH.move(fallingSand);
      char destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHWEST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHEAST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      g.set(fallingSand, 'o');
      fallingSand = sandOrigin;
//      System.out.println(g.toString());
    }
//    System.out.println(g.countChar('#'));
    return g.countChar('o');
  }

  @Override
  public Object part2() {
    List<List<Point>> in = dayStream().map(s -> Arrays.asList(s.split(" -> ")).stream().map(s2 -> readString(s2, "%n,%n", Pos.class)).map(p -> new Point(Math.toIntExact(p.x), Math.toIntExact(p.y))).toList()).collect(Collectors.toCollection(ArrayList::new));
    CharGrid g = new CharGrid('.', 1000, 1000);
    int maxY = in.stream().flatMapToInt(e -> e.stream().mapToInt(f -> f.y)).max().getAsInt() + 2;
    in.add(List.of(new Point(0, maxY), new Point(999, maxY)));
    for(List<Point> rock : in) {
      for(int i = 1; i<rock.size(); i++) {
        Point p1 = rock.get(i-1);
        Point p2 = rock.get(i);
        if(p1.x != p2.x) {
          int from = Math.min(p1.x, p2.x);
          int to = Math.max(p1.x, p2.x);
          for(int j = from; j<=to; j++){
            g.set(new Point(j, p1.y), '#');
          }
        } else if(p1.y != p2.y) {
          int from = Math.min(p1.y, p2.y);
          int to = Math.max(p1.y, p2.y);
          for(int j = from; j<=to; j++){
            g.set(new Point(p1.x, j), '#');
          }
        }
      }
    }
    Point sandOrigin = new Point(500, 0);
    g.set(sandOrigin, '+');
    Point fallingSand = sandOrigin;
//    g.set(fallingSand, 'o');
    while(g.get(sandOrigin) != 'o') {
      Point moveTo = Direction.SOUTH.move(fallingSand);
      char destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHWEST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      moveTo = Direction.SOUTHEAST.move(fallingSand);
      destination = g.get(moveTo);
      if(destination == '.') {
        fallingSand = moveTo;
        continue;
      }
      g.set(fallingSand, 'o');
      fallingSand = sandOrigin;
//      System.out.println(g.toString());
    }
//    System.out.println(g.countChar('#'));
    return g.countChar('o');
  }
}
