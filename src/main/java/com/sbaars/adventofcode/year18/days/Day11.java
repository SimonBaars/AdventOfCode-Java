package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year18.Day2018;

public class Day11 extends Day2018 {

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    return findSolution(false);
  }

  @Override
  public Object part2() {
    return findSolution(true);
  }

  private String findSolution(boolean part2) {
    long in = dayNumbers()[0];
    NumGrid g = new NumGrid(new long[300][300]);
    for(int y = 0; y<g.sizeX(); y++) {
      for(int x = 0; x<g.sizeY(); x++) {
        long rackId = x + 11;
        g.grid[y][x] = get100Digit(((rackId * (y + 1)) + in) * rackId) - 5;
      }
    }
    Pair<Long, Loc3D> top = new Pair<>(Long.MIN_VALUE, null);
    int SQUARE_SIZE = 3;
    for(int size = part2 ? 1 : SQUARE_SIZE; size <= (part2 ? g.sizeX()-1 : SQUARE_SIZE); size++) {
      for (int y = 0; y <= g.sizeX() - size; y++) {
        for (int x = 0; x <= g.sizeY() - size; x++) {
          long sum = 0;
          for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
              sum += g.grid[y + j][x + i];
            }
          }
          if(sum > top.a()) {
            top = new Pair<>(sum, new Loc3D(x + 1, y + 1, size));
          }
        }
      }
    }
    String res = top.b().toString().replace(" ", "");
    return part2 ? res : res.substring(0, res.length() - 2);
  }

  public long get100Digit(long n) {
    return n > 100 ? (n/100)%10 : 0;
  }
}
