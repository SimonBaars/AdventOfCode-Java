package com.sbaars.adventofcode.common;

import static java.lang.Math.abs;

import java.awt.*;
import java.util.Arrays;

public enum Direction {
  NORTH(1, 'U'), EAST(4, 'R'), SOUTH(2, 'D'), WEST(3, 'L'),
  NORTHEAST(4, 'E'), EASTSOUTH(5, 'E'), SOUTHWEST(6, 'E'), WESTNORTH(7, 'E');

  public final int num;
  public final int code;

  Direction(int num, char code) {
    this.num = num;
    this.code = code;
  }

  public static Direction getByDirCode(char code) {
    return Arrays.stream(values()).filter(e -> e.code == code).findAny().get();
  }

  public static Direction getByDir(char code) {
    return Arrays.stream(values()).filter(e -> e.name().charAt(0) == code).findAny().get();
  }

  public static Direction getByMove(Point from, Point to) {
    if (to.x > from.x) return EAST;
    else if (to.x < from.x) return WEST;
    else if (to.y > from.y) return SOUTH;
    else if (to.y < from.y) return NORTH;
    throw new IllegalStateException("From and to location are the same: " + from + ", " + to);
  }

  public static Point turn(Point w, boolean b) {
    return b ? new Point(-w.y, w.x) : new Point(w.y, -w.x);
  }

  public static Point turnDegrees(Point w, int distance, boolean b) {
    int num = distance % 360;
    while (num > 0) {
      w = turn(w, b);
      num -= 90;
    }
    return w;
  }

  public static Point turnDegrees(Point w, int distance) {
    return turnDegrees(w, abs(distance), distance > 0);
  }

  public static Direction[] fourDirections() {
    return new Direction[]{NORTH, EAST, SOUTH, WEST};
  }

  public Direction turn(boolean right) {
    int cur = ordinal() + (right ? 1 : -1);
    if (cur == fourDirections().length) cur = 0;
    else if (cur == -1) cur = 3;
    return fourDirections()[cur];
  }

  public Point move(Point currentLocation, int amount) {
    switch (this) {
      case SOUTH:
        return new Point(currentLocation.x, currentLocation.y + amount);
      case NORTH:
        return new Point(currentLocation.x, currentLocation.y - amount);
      case EAST:
        return new Point(currentLocation.x + amount, currentLocation.y);
      case WEST:
        return new Point(currentLocation.x - amount, currentLocation.y);
      case SOUTHWEST:
        return new Point(currentLocation.x - amount, currentLocation.y + amount);
      case NORTHEAST:
        return new Point(currentLocation.x + amount, currentLocation.y - amount);
      case EASTSOUTH:
        return new Point(currentLocation.x + amount, currentLocation.y + amount);
      case WESTNORTH:
        return new Point(currentLocation.x - amount, currentLocation.y - amount);
    }
    throw new IllegalStateException("Non-existent Direction: " + this);
  }

  public char getInGrid(char[][] grid, Point p, char none) {
    if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
      return grid[p.x][p.y];
    }
    return none;
  }

  public long getInGrid(long[][] grid, Point p, int none) {
    p = this.move(p);
    if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
      return grid[p.x][p.y];
    }
    return none;
  }

  public char getInGrid(char[][] grid, Point p) {
    return getInGrid(grid, p, '.');
  }

  public Point move(Point currentLocation) {
    return move(currentLocation, 1);
  }

  public Direction opposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case NORTHEAST:
        return SOUTHWEST;
      case SOUTHWEST:
        return NORTHEAST;
      case EASTSOUTH:
        return WESTNORTH;
      case WESTNORTH:
        return EASTSOUTH;
    }
    throw new IllegalStateException("Non-existent Direction: " + this);
  }

  public boolean leftOf(Direction robotDir) {
    int n = this.ordinal() - 1;
    if (n == -1) n = values().length - 1;
    return robotDir.ordinal() == n;
  }

  public Direction turnDegrees(int degrees, boolean right) {
    int num = degrees % 360;
    Direction dir = this;
    while (num > 0) {
      dir = turn(right);
      num -= 90;
    }
    return dir;
  }

  public Direction turnDegrees(int degrees) {
    return turnDegrees(abs(degrees), degrees > 0);
  }
}
