package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year16.Day2016;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 extends Day2016 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    Day d = new Day1();
    d.downloadIfNotDownloaded();
    d.printParts();
//    d.submitPart1();
    d.submitPart2();
  }

  record Move(boolean right, int dist) {
  }

  @Override
  public Object part1() {
    List<Move> moves = dayStream(", ")
        .map(String::trim)
        .map(s -> new Move(s.charAt(0) == 'R', Integer.parseInt(s.substring(1))))
        .toList();
    Point p = new Point(0, 0);
    Direction dir = Direction.NORTH;
    for (Move m : moves) {
      dir = dir.turn(m.right);
      p = dir.moveFix(p, m.dist);
    }
    return Math.abs(p.x) + Math.abs(p.y);
  }

  @Override
  public Object part2() {
    List<Move> moves = dayStream(", ")
        .map(String::trim)
        .map(s -> new Move(s.charAt(0) == 'R', Integer.parseInt(s.substring(1))))
        .toList();
    Point p = new Point(0, 0);
    Direction dir = Direction.NORTH;
    Set<Point> visited = new HashSet<>();
    visited.add(p);
    for (Move m : moves) {
      dir = dir.turn(m.right);
      for (int i = 0; i < m.dist; i++) {
        p = dir.moveFix(p, 1);
        if (!visited.add(p)) {
          return Math.abs(p.x) + Math.abs(p.y);
        }
      }
    }
    return 0;
  }
}
