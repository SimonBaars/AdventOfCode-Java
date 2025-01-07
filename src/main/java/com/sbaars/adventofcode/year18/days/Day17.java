package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends Day2018 {
  private static final int SPRING_X = 500;
  private static final byte CLAY = '#';
  private static final byte WATER = '~';
  private static final byte FLOWING = '|';
  private static final byte SAND = '.';
  
  private byte[][] grid;
  private int minX, maxX, minY, maxY;
  private int width, height;
  private BitSet flowCache;

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
    width = maxX - minX + 1;
    height = maxY + 1;
    
    grid = new byte[height][width];
    for (byte[] row : grid) {
      Arrays.fill(row, SAND);
    }

    for (int[] coord : clayCoords) {
      grid[coord[1]][coord[0] - minX] = CLAY;
    }
    
    flowCache = new BitSet(width * height);
  }

  private int getCacheIndex(int x, int y) {
    return y * width + x;
  }

  private boolean hasFlowed(int x, int y) {
    return flowCache.get(getCacheIndex(x, y));
  }

  private void markFlowed(int x, int y) {
    flowCache.set(getCacheIndex(x, y));
  }

  private void flow(int x, int y) {
    if (y >= height || x < 0 || x >= width || grid[y][x] == CLAY) {
      return;
    }

    if (hasFlowed(x, y)) {
      return;
    }
    markFlowed(x, y);

    if (grid[y][x] == SAND) {
      grid[y][x] = FLOWING;
    }

    if (y + 1 < height) {
      byte below = grid[y + 1][x];
      if (below != CLAY && below != WATER) {
        flow(x, y + 1);
        if (grid[y + 1][x] == FLOWING) {
          grid[y][x] = FLOWING;
          return;
        }
      }
    } else {
      grid[y][x] = FLOWING;
      return;
    }

    int left = x;
    int right = x;
    byte[] row = grid[y];
    byte[] rowBelow = grid[y + 1];

    while (left > 0 && row[left - 1] != CLAY) {
      left--;
      if (rowBelow[left] != CLAY && rowBelow[left] != WATER) {
        break;
      }
    }
    boolean leftWall = left > 0 && row[left - 1] == CLAY;

    while (right < width - 1 && row[right + 1] != CLAY) {
      right++;
      if (rowBelow[right] != CLAY && rowBelow[right] != WATER) {
        break;
      }
    }
    boolean rightWall = right < width - 1 && row[right + 1] == CLAY;

    byte waterType = (leftWall && rightWall) ? WATER : FLOWING;
    Arrays.fill(row, left, right + 1, waterType);

    if (waterType == WATER && y > 0) {
      flow(x, y - 1);
    } else {
      if (!leftWall) flow(left, y);
      if (!rightWall) flow(right, y);
    }
  }

  private void simulateWater() {
    flow(SPRING_X - minX, 0);
  }

  private int countWater() {
    int total = 0;
    for (int y = minY; y <= maxY; y++) {
      byte[] row = grid[y];
      for (int x = 0; x < width; x++) {
        byte cell = row[x];
        if (cell == WATER || cell == FLOWING) {
          total++;
        }
      }
    }
    return total;
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
      byte[] row = grid[y];
      for (int x = 0; x < width; x++) {
        if (row[x] == WATER) {
          count++;
        }
      }
    }
    return count;
  }
}
