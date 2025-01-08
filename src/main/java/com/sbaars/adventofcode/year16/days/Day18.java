package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

public class Day18 extends Day2016 {
  private static final int TOTAL_ROWS = 40;
  private static final char TRAP = '^';
  private static final char SAFE = '.';

  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  private boolean isTrap(char left, char center, char right) {
    return (left == TRAP && center == TRAP && right == SAFE) ||
           (center == TRAP && right == TRAP && left == SAFE) ||
           (left == TRAP && center == SAFE && right == SAFE) ||
           (right == TRAP && center == SAFE && left == SAFE);
  }

  private String generateNextRow(String currentRow) {
    StringBuilder nextRow = new StringBuilder();
    for (int i = 0; i < currentRow.length(); i++) {
      char left = i > 0 ? currentRow.charAt(i - 1) : SAFE;
      char center = currentRow.charAt(i);
      char right = i < currentRow.length() - 1 ? currentRow.charAt(i + 1) : SAFE;
      nextRow.append(isTrap(left, center, right) ? TRAP : SAFE);
    }
    return nextRow.toString();
  }

  private int countSafeTiles(String firstRow, int rows) {
    String currentRow = firstRow;
    int safeTiles = countSafeInRow(currentRow);

    for (int i = 1; i < rows; i++) {
      currentRow = generateNextRow(currentRow);
      safeTiles += countSafeInRow(currentRow);
    }

    return safeTiles;
  }

  private int countSafeInRow(String row) {
    int count = 0;
    for (char c : row.toCharArray()) {
      if (c == SAFE) count++;
    }
    return count;
  }

  @Override
  public Object part1() {
    return countSafeTiles(day().trim(), TOTAL_ROWS);
  }

  @Override
  public Object part2() {
    return "";
  }
}
