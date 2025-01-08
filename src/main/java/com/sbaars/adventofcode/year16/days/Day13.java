package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;

public class Day13 extends Day2016 {
  private static final int TARGET_X = 31;
  private static final int TARGET_Y = 39;
  private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  private static class Point {
    final int x, y;
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Point)) return false;
      Point point = (Point) o;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  private boolean isWall(int x, int y, int favoriteNumber) {
    if (x < 0 || y < 0) return true;
    long value = x*x + 3*x + 2*x*y + y + y*y + favoriteNumber;
    return Long.bitCount(value) % 2 == 1;
  }

  private int findShortestPath() {
    int favoriteNumber = Integer.parseInt(day().trim());
    Queue<Point> queue = new LinkedList<>();
    Map<Point, Integer> distances = new HashMap<>();
    Point start = new Point(1, 1);
    queue.add(start);
    distances.put(start, 0);

    while (!queue.isEmpty()) {
      Point current = queue.poll();
      int currentDist = distances.get(current);

      if (current.x == TARGET_X && current.y == TARGET_Y) {
        return currentDist;
      }

      for (int[] dir : DIRECTIONS) {
        int newX = current.x + dir[0];
        int newY = current.y + dir[1];
        Point next = new Point(newX, newY);

        if (!isWall(newX, newY, favoriteNumber) && !distances.containsKey(next)) {
          distances.put(next, currentDist + 1);
          queue.add(next);
        }
      }
    }

    return -1;
  }

  @Override
  public Object part1() {
    return findShortestPath();
  }

  @Override
  public Object part2() {
    return "";
  }
}
