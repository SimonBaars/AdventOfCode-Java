package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.Arrays;
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
    screen[row] = newRow;
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

  private int countLitPixels(boolean[][] screen) {
    int count = 0;
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        if (screen[y][x]) count++;
      }
    }
    return count;
  }

  @Override
  public Object part1() {
    boolean[][] screen = new boolean[HEIGHT][WIDTH];
    
    for (String instruction : dayStream().toList()) {
      Matcher m;
      if ((m = RECT_PATTERN.matcher(instruction)).matches()) {
        rect(screen, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      } else if ((m = ROTATE_ROW_PATTERN.matcher(instruction)).matches()) {
        rotateRow(screen, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      } else if ((m = ROTATE_COL_PATTERN.matcher(instruction)).matches()) {
        rotateColumn(screen, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      }
    }

    return countLitPixels(screen);
  }

  @Override
  public Object part2() {
    return "";
  }
}
