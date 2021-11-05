package com.sbaars.adventofcode.common;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class InfiniteGrid {
  final Map<Point, Character> grid = new HashMap<>();
  Point currentPos = new Point(0, 0);

  public InfiniteGrid(char[][] g){
    for(int i = 0; i<g.length; i++){
      for(int j = 0; j<g[0].length; j++){
        grid.put(new Point(j, i), g[i][j]);
      }
    }
  }

  public int countChar(char...c){
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate(){
    return Arrays.stream(grid).map(String::new).flatMapToInt(String::chars);
  }
}
