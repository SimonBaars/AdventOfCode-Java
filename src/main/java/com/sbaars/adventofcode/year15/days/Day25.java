package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;

public class Day25 extends Day2015 {
  private static final long FIRST_CODE = 20151125L;
  private static final long MULTIPLIER = 252533L;
  private static final long DIVISOR = 33554393L;

  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    Day25 day = new Day25();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 25, 1);
  }

  @Override
  public Object part1() {
    String input = day().trim();
    int targetRow = Integer.parseInt(input.replaceAll(".*row (\\d+).*", "$1"));
    int targetCol = Integer.parseInt(input.replaceAll(".*column (\\d+).*", "$1"));
    return findCode(targetRow, targetCol);
  }

  @Override
  public Object part2() {
    return "Merry Christmas!"; // There is no part 2 for Day 25
  }

  private long findCode(int targetRow, int targetCol) {
    // Calculate the position in the sequence
    int position = getPositionInSequence(targetRow, targetCol);
    
    // Calculate the code at that position
    long code = FIRST_CODE;
    for (int i = 1; i < position; i++) {
      code = (code * MULTIPLIER) % DIVISOR;
    }
    return code;
  }

  private int getPositionInSequence(int row, int col) {
    // The position is the sum of all numbers in the diagonal before the target position
    // plus the position in the current diagonal
    int diagonalNum = row + col - 1;
    int positionsBeforeDiagonal = (diagonalNum * (diagonalNum - 1)) / 2;
    return positionsBeforeDiagonal + col;
  }
}
