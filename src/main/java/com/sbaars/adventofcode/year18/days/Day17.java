package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends Day2018 {
  private static final int SPRING_X = 500;
  private static final char CLAY = '#';
  private static final char WATER = '~';
  private static final char FLOWING = '|';
  private static final char SAND = '.';
  
  private char[][] grid;
  private int minX, maxX, minY, maxY;

  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  private void parseInput() {
    List<String> lines = dayStream().toList();
    Set<int[]> clayCoords = new HashSet<>();
    minX = Integer.MAX_VALUE;
    maxX = Integer.MIN_VALUE;
    minY = Integer.MAX_VALUE;
    maxY = Integer.MIN_VALUE;

    Pattern pattern = Pattern.compile("([xy])=(\\d+), ([xy])=(\\d+)\\.\\.(\\d+)");
    for (String line : lines) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        boolean isX = m.group(1).equals("x");
        int fixed = Integer.parseInt(m.group(2));
        int rangeStart = Integer.parseInt(m.group(4));
        int rangeEnd = Integer.parseInt(m.group(5));

        for (int i = rangeStart; i <= rangeEnd; i++) {
          int x = isX ? fixed : i;
          int y = isX ? i : fixed;
          clayCoords.add(new int[]{x, y});
          minX = Math.min(minX, x);
          maxX = Math.max(maxX, x);
          minY = Math.min(minY, y);
          maxY = Math.max(maxY, y);
        }
      }
    }

    minX -= 2;
    maxX += 2;
    
    grid = new char[maxY + 1][maxX - minX + 1];
    for (char[] row : grid) {
      Arrays.fill(row, SAND);
    }

    for (int[] coord : clayCoords) {
      grid[coord[1]][coord[0] - minX] = CLAY;
    }
  }

  private boolean isBlocked(int x, int y) {
    if (y >= grid.length || x < 0 || x >= grid[0].length) return false;
    return grid[y][x] == CLAY || grid[y][x] == WATER;
  }

  private boolean canFlowDown(int x, int y) {
    if (y + 1 >= grid.length) return true;
    char below = grid[y + 1][x];
    return below != CLAY && below != WATER;
  }

  private void flow(int x, int y) {
    if (y >= grid.length || x < 0 || x >= grid[0].length || grid[y][x] == CLAY) {
      return;
    }

    if (grid[y][x] == SAND) {
      grid[y][x] = FLOWING;
    }

    if (canFlowDown(x, y)) {
      if (y + 1 < grid.length) {
        flow(x, y + 1);
      }
      return;
    }

    boolean leftWall = false;
    boolean rightWall = false;
    int left = x;
    int right = x;

    while (left >= 0 && grid[y][left] != CLAY && isBlocked(left, y + 1)) {
      left--;
    }
    leftWall = left >= 0 && grid[y][left] == CLAY;

    while (right < grid[0].length && grid[y][right] != CLAY && isBlocked(right, y + 1)) {
      right++;
    }
    rightWall = right < grid[0].length && grid[y][right] == CLAY;

    if (leftWall && rightWall) {
      for (int i = left + 1; i < right; i++) {
        grid[y][i] = WATER;
      }
      if (y > 0) {
        flow(x, y - 1);
      }
    } else {
      for (int i = left + 1; i < right; i++) {
        grid[y][i] = FLOWING;
      }
      if (!leftWall && left >= 0) {
        flow(left, y);
      }
      if (!rightWall && right < grid[0].length) {
        flow(right, y);
      }
    }
  }

  private void simulateWater() {
    flow(SPRING_X - minX, 0);
  }

  private int countWater() {
    int flowingCount = 0;
    int settledCount = 0;
    for (int y = minY; y <= maxY; y++) {
      for (int x = 0; x < grid[0].length; x++) {
        if (grid[y][x] == WATER) {
          settledCount++;
        } else if (grid[y][x] == FLOWING) {
          flowingCount++;
        }
      }
    }
    return flowingCount + settledCount;
  }

  @Override
  public Object part1() {
    parseInput();
    simulateWater();
    return countWater();
  }

  @Override
  public Object part2() {
    int count = 0;
    for (int y = minY; y <= maxY; y++) {
      for (int x = 0; x < grid[0].length; x++) {
        if (grid[y][x] == WATER) {
          count++;
        }
      }
    }
    return count;
  }
}
