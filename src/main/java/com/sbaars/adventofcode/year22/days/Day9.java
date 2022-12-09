package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day9 extends Day2022 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  public record Move(char dir, long n){}

  @Override
  public Object part1() {
    return simulateRope(1);
  }

  @Override
  public Object part2() {
    return simulateRope(9);
  }

  private int simulateRope(int size) {
    List<Move> moves = dayStream().map(s -> readString(s, "%c %n", Move.class)).toList();
    Point head = new Point();
    List<Point> tail = new java.util.ArrayList<>(IntStream.range(0, size).mapToObj(i -> new Point()).toList());
    HashSet<Point> visited = new HashSet<>();
    visited.add(head);

    for(Move m : moves) {
      Direction dir = Direction.getByDirCode(m.dir);
      for(int i = 0; i<m.n; i++) {
        head = dir.move(head);
        for(int j = 0; j<tail.size(); j++) {
          Point t = j == 0 ? head : tail.get(j - 1);
          tail.set(j, moveRope(t, tail.get(j)));
          if(j == tail.size()-1) visited.add(tail.get(j));
        }
      }
    }
    return visited.size();
  }

  private static Point moveRope(Point head, Point tail) {
    if (Arrays.stream(Direction.values()).noneMatch(d -> d.move(tail).equals(head))) {
      if (head.x > tail.x && head.y == tail.y) {
        return Direction.EAST.move(tail);
      } else if (head.x < tail.x && head.y == tail.y) {
        return Direction.WEST.move(tail);
      } else if (head.x == tail.x && head.y > tail.y) {
        return Direction.SOUTH.move(tail);
      } else if (head.x == tail.x && head.y < tail.y) {
        return Direction.NORTH.move(tail);
      } else if (head.x > tail.x && head.y > tail.y) {
        return Direction.SOUTHEAST.move(tail);
      } else if (head.x < tail.x && head.y < tail.y) {
        return Direction.NORTHWEST.move(tail);
      } else if (head.x < tail.x && head.y > tail.y) {
        return Direction.SOUTHWEST.move(tail);
      } else if (head.x > tail.x && head.y < tail.y) {
        return Direction.NORTHEAST.move(tail);
      } else throw new IllegalStateException();
    }
    return tail;
  }
}
