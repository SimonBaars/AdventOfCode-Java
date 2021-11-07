package com.sbaars.adventofcode.year20.days;

import static java.util.Arrays.stream;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year20.Day2020;
import java.awt.*;

public class Day11 extends Day2020 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    char[][] input = dayGrid();
    int changes;
    do {
      changes = 0;
      char[][] newGrid = new char[input.length][input[0].length];
      for (int i = 0; i < input.length; i++) {
        for (int j = 0; j < input[0].length; j++) {
          Point p = new Point(i, j);
          char[][] finalInput = input;
          long occupied = stream(Direction.values()).filter(d ->
              d.getInGrid(finalInput, d.move(p)) == '#'
          ).count();
          changes = getChanges(input, changes, newGrid, i, j, occupied, 4L);
        }
      }
      input = newGrid;
    } while (changes > 0);
    return getCount(input, '#');
  }

  private int getChanges(char[][] input, int changes, char[][] newGrid, int i, int j, long occupied, long numOccupied) {
    if (occupied == 0L && input[i][j] == 'L') {
      newGrid[i][j] = '#';
      changes++;
    } else if (occupied >= numOccupied && input[i][j] == '#') {
      newGrid[i][j] = 'L';
      changes++;
    } else {
      newGrid[i][j] = input[i][j];
    }
    return changes;
  }

  private long getCount(char[][] input, char ch) {
    return stream(input).flatMapToInt(c -> new String(c).chars()).filter(c -> c == ch).count();
  }

  @Override
  public Object part2() {
    char[][] input = dayGrid();
    int changes;
    do {
      changes = 0;
      char[][] newGrid = new char[input.length][input[0].length];
      for (int i = 0; i < input.length; i++) {
        for (int j = 0; j < input[0].length; j++) {
          int occupied = countOccupied(input, i, j);
          changes = getChanges(input, changes, newGrid, i, j, occupied, 5L);
        }
      }
      input = newGrid;
    } while (changes > 0);
    return getCount(input, '#');
  }

  private int countOccupied(char[][] input, int i, int j) {
    int occupied = 0;
    Direction[] dirs = Direction.values();
    for (Direction dir : dirs) {
      Point p = new Point(i, j);
      p = dir.move(p, 1);
      while (p.x >= 0 && p.x < input.length && p.y >= 0 && p.y < input[0].length && input[p.x][p.y] != 'L') {
        if (input[p.x][p.y] == '#') {
          occupied++;
          break;
        }
        p = dir.move(p, 1);
      }
    }
    return occupied;
  }
}
