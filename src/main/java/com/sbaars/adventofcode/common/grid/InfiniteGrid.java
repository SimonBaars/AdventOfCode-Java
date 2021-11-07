package com.sbaars.adventofcode.common.grid;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class InfiniteGrid implements Grid {
  final Map<Point, Character> grid = new HashMap<>();
  Point currentPos = new Point(0, 0);

  public InfiniteGrid(char[][] g){
    for(int i = 0; i<g.length; i++){
      for(int j = 0; j<g[0].length; j++){
        grid.put(new Point(j, i), g[i][j]);
      }
    }
  }

  public IntStream iterate(){
    return grid.values().stream().mapToInt(Character::getNumericValue);
  }
}
