package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18 extends Day2018 {
  private static final char OPEN = '.';
  private static final char TREES = '|';
  private static final char LUMBERYARD = '#';

  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  private InfiniteGrid simulate(InfiniteGrid grid) {
    InfiniteGrid newGrid = new InfiniteGrid();
    long maxX = grid.maxX();
    long maxY = grid.maxY();

    for (long y = 0; y <= maxY; y++) {
      for (long x = 0; x <= maxX; x++) {
        Loc current = new Loc(x, y);
        char currentAcre = grid.getChar(current);
        long trees = grid.around(current, true).map(grid::getChar).filter(c -> c == TREES).count();
        long lumberyards = grid.around(current, true).map(grid::getChar).filter(c -> c == LUMBERYARD).count();

        char newAcre = currentAcre;
        if (currentAcre == OPEN && trees >= 3) {
          newAcre = TREES;
        } else if (currentAcre == TREES && lumberyards >= 3) {
          newAcre = LUMBERYARD;
        } else if (currentAcre == LUMBERYARD && !(lumberyards >= 1 && trees >= 1)) {
          newAcre = OPEN;
        }
        newGrid.set(current, newAcre);
      }
    }
    return newGrid;
  }

  private int calculateResourceValue(InfiniteGrid grid) {
    int trees = 0;
    int lumberyards = 0;
    for (long y = 0; y <= grid.maxY(); y++) {
      for (long x = 0; x <= grid.maxX(); x++) {
        char acre = grid.getChar(new Loc(x, y));
        if (acre == TREES) trees++;
        else if (acre == LUMBERYARD) lumberyards++;
      }
    }
    return trees * lumberyards;
  }

  @Override
  public Object part1() {
    InfiniteGrid grid = new InfiniteGrid(dayGrid());
    for (int i = 0; i < 10; i++) {
      grid = simulate(grid);
    }
    return calculateResourceValue(grid);
  }

  @Override
  public Object part2() {
    InfiniteGrid grid = new InfiniteGrid(dayGrid());
    Map<String, Integer> seen = new HashMap<>();
    List<Integer> values = new ArrayList<>();
    
    int minute = 0;
    String state = grid.toString();
    while (!seen.containsKey(state)) {
      seen.put(state, minute);
      values.add(calculateResourceValue(grid));
      grid = simulate(grid);
      minute++;
      state = grid.toString();
    }
    
    int cycleStart = seen.get(state);
    int cycleLength = minute - cycleStart;
    long remaining = (1000000000L - cycleStart) % cycleLength;
    
    return values.get((int)(cycleStart + remaining));
  }
}
