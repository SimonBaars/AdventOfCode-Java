package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 extends Day2016 {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 6;
  private static final Pattern RECT_PATTERN = Pattern.compile("rect (\\d+)x(\\d+)");
  private static final Pattern ROTATE_ROW_PATTERN = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
  private static final Pattern ROTATE_COL_PATTERN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  private void rect(boolean[][] screen, int width, int height) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        screen[y][x] = true;
      }
    }
  }

  private void rotateRow(boolean[][] screen, int row, int amount) {
    boolean[] newRow = new boolean[WIDTH];
    for (int x = 0; x < WIDTH; x++) {
      newRow[(x + amount) % WIDTH] = screen[row][x];
    }
    System.arraycopy(newRow, 0, screen[row], 0, WIDTH);
  }

  private void rotateColumn(boolean[][] screen, int col, int amount) {
    boolean[] newCol = new boolean[HEIGHT];
    for (int y = 0; y < HEIGHT; y++) {
      newCol[(y + amount) % HEIGHT] = screen[y][col];
    }
    for (int y = 0; y < HEIGHT; y++) {
      screen[y][col] = newCol[y];
    }
  }

  private boolean[][] processInstructions() {
    boolean[][] screen = new boolean[HEIGHT][WIDTH];
    
    for (String instruction : dayStream().toList()) {
      Matcher rectMatcher = RECT_PATTERN.matcher(instruction);
      Matcher rowMatcher = ROTATE_ROW_PATTERN.matcher(instruction);
      Matcher colMatcher = ROTATE_COL_PATTERN.matcher(instruction);

      if (rectMatcher.matches()) {
        rect(screen, Integer.parseInt(rectMatcher.group(1)), Integer.parseInt(rectMatcher.group(2)));
      } else if (rowMatcher.matches()) {
        rotateRow(screen, Integer.parseInt(rowMatcher.group(1)), Integer.parseInt(rowMatcher.group(2)));
      } else if (colMatcher.matches()) {
        rotateColumn(screen, Integer.parseInt(colMatcher.group(1)), Integer.parseInt(colMatcher.group(2)));
      }
    }

    return screen;
  }

  private int countLitPixels(boolean[][] screen) {
    int count = 0;
    for (boolean[] row : screen) {
      for (boolean pixel : row) {
        if (pixel) count++;
      }
    }
    return count;
  }

  private String displayScreen(boolean[][] screen) {
    StringBuilder sb = new StringBuilder("\n");
    for (boolean[] row : screen) {
      for (boolean pixel : row) {
        sb.append(pixel ? '#' : '.');
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  @Override
  public Object part1() {
    return countLitPixels(processInstructions());
  }

  @Override
  public Object part2() {
    return displayScreen(processInstructions());
  }
}
