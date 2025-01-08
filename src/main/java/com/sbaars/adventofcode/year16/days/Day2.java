package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

public class Day2 extends Day2016 {
  private static final int[][] KEYPAD_1 = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
  };

  private static final char[][] KEYPAD_2 = {
    {' ', ' ', '1', ' ', ' '},
    {' ', '2', '3', '4', ' '},
    {'5', '6', '7', '8', '9'},
    {' ', 'A', 'B', 'C', ' '},
    {' ', ' ', 'D', ' ', ' '}
  };

  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  private String findCode(int[][] keypad, int startX, int startY) {
    StringBuilder code = new StringBuilder();
    int x = startX, y = startY;

    for (String line : dayStream().toList()) {
      for (char move : line.toCharArray()) {
        int newX = x, newY = y;
        switch (move) {
          case 'U' -> newX--;
          case 'D' -> newX++;
          case 'L' -> newY--;
          case 'R' -> newY++;
        }
        if (newX >= 0 && newX < keypad.length && newY >= 0 && newY < keypad[0].length && keypad[newX][newY] != 0) {
          x = newX;
          y = newY;
        }
      }
      code.append(keypad[x][y]);
    }

    return code.toString();
  }

  private String findCode2(char[][] keypad, int startX, int startY) {
    StringBuilder code = new StringBuilder();
    int x = startX, y = startY;

    for (String line : dayStream().toList()) {
      for (char move : line.toCharArray()) {
        int newX = x, newY = y;
        switch (move) {
          case 'U' -> newX--;
          case 'D' -> newX++;
          case 'L' -> newY--;
          case 'R' -> newY++;
        }
        if (newX >= 0 && newX < keypad.length && newY >= 0 && newY < keypad[0].length && keypad[newX][newY] != ' ') {
          x = newX;
          y = newY;
        }
      }
      code.append(keypad[x][y]);
    }

    return code.toString();
  }

  @Override
  public Object part1() {
    return findCode(KEYPAD_1, 1, 1);
  }

  @Override
  public Object part2() {
    return findCode2(KEYPAD_2, 2, 0);
  }
}
