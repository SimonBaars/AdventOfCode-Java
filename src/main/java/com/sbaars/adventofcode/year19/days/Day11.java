package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.ProcessesImages;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Day11 extends Day2019 implements ProcessesImages {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    return robotWalk(false);
  }

  private Object robotWalk(boolean startWhite) {
    IntcodeComputer c = new IntcodeComputer(11);
    Point currentLocation = new Point(0, 0);
    Direction dir = Direction.NORTH;
    final Set<Point> paintedOnce = new HashSet<>();
    final Set<Point> whitePlaces = new HashSet<>();
    if (startWhite)
      whitePlaces.add(currentLocation);
    while (true) {
      c.setInput(whitePlaces.contains(currentLocation) ? 1 : 0);
      long paintColor = c.run();
      if (paintColor == IntcodeComputer.STOP_CODE)
        break;
      long turn = c.run();
      paintedOnce.add(currentLocation);
      if (paintColor == 1L) {
        whitePlaces.add(currentLocation);
      } else if (paintColor == 0L) {
        whitePlaces.remove(currentLocation);
      }

      dir = dir.turn(turn == 1L);
      currentLocation = dir.move(currentLocation);
    }
    return startWhite ? constructImage(whitePlaces) : paintedOnce.size();
  }

  private String constructImage(Set<Point> whitePlaces) {
    int cornerX = whitePlaces.stream().mapToInt(e -> e.x).min().getAsInt();
    int cornerY = whitePlaces.stream().mapToInt(e -> e.y).min().getAsInt();
    whitePlaces.forEach(e -> e.move(e.x - cornerX, e.y - cornerY));
    int sizex = whitePlaces.stream().mapToInt(e -> e.x).max().getAsInt() + 1;
    int sizey = whitePlaces.stream().mapToInt(e -> e.y).max().getAsInt() + 1;
    int[][] places = new int[sizey][sizex];
    for (Point p : whitePlaces)
      places[p.y][p.x] = 1;
    return printAsciiArray(places);
  }

  @Override
  public Object part2() {
    return robotWalk(true);
  }
}
