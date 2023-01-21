package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.year21.Day2021;

public class Day25 extends Day2021 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    Day d = new Day25();
    d.downloadIfNotDownloaded();
    d.printParts();
  }

  @Override
  public Object part1() {
    var in = dayGrid();
    CharGrid g = new CharGrid(in);
    for (int x = 1; ; x++) {
      var g2 = g.copy();
      for (int i = 0; i < g.grid[0].length; i++) {
        for (int j = 0; j < g.grid.length; j++) {
          if (g.grid[j][i] == '>' && g.grid[j][(i + 1) % g.grid[0].length] == '.') {
            g2.grid[j][i] = '.';
            g2.grid[j][(i + 1) % g.grid[0].length] = '>';
          }
        }
      }
      var g3 = g2.copy();
      for (int i = 0; i < g2.grid[0].length; i++) {
        for (int j = 0; j < g2.grid.length; j++) {
          if (g2.grid[j][i] == 'v' && g2.grid[(j + 1) % g2.grid.length][i] == '.') {
            g3.grid[j][i] = '.';
            g3.grid[(j + 1) % g.grid.length][i] = 'v';
          }
        }
      }
      if (g.equals(g3)) {
        return x;
      }
      g = g3;
      if (x > 500) {
        return x;
      }
    }
  }

  @Override
  public Object part2() {
    return "Merry Christmas";
  }
}
