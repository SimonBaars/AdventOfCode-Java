package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.Map;

import static com.sbaars.adventofcode.util.AOCUtils.findMax;

public class Day11 extends Day2018 {
  private final int SQUARE_SIZE = 3;

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  @Override
  public Object part1() {
    long in = dayNumbers()[0];
    NumGrid g = new NumGrid(new long[300][300]);
    for(int y = 0; y<g.sizeX(); y++) {
      for(int x = 0; x<g.sizeY(); x++) {
        long rackId = x + 11;
        g.grid[y][x] = get100Digit(((rackId * (y + 1)) + in) * rackId) - 5;
      }
    }
    LongCountMap<Loc> lcm = new LongCountMap<>();
    for(int y = 0; y<=g.sizeX()-SQUARE_SIZE; y++) {
      for (int x = 0; x<=g.sizeY()-SQUARE_SIZE; x++) {
        Loc l = new Loc(x + 1, y + 1);
        for(int i = 0; i<SQUARE_SIZE; i++) {
          for(int j = 0; j<SQUARE_SIZE; j++) {
            lcm.increment(l, g.grid[y+j][x+i]);
          }
        }
      }
    }
    return findMax(lcm.entrySet(), Map.Entry::getValue).getKey().toString().replace(" ", "");
  }

  public long get100Digit(long n) {
    return n > 100 ? (n/100)%10 : 0;
  }

  @Override
  public Object part2() {
    return "";
  }
}
