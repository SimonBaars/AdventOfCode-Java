package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;

public class Day3 extends Day2020 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    return trees(dayGrid(), 3, 1);
  }

  @Override
  public Object part2() {
    char[][] g = dayGrid();
    return trees(g, 1, 1) * trees(g, 3, 1) * trees(g, 5, 1) * trees(g, 7, 1) * trees(g, 1, 2);
  }

  int trees(char[][] grid, int x, int y) {
    int trees = 0;
    for (int i = 0; i * y < grid.length; i++) {
      if (grid[i * y][i * x % grid[0].length] == '#') {
        trees++;
      }
    }
    return trees;
  }
}
