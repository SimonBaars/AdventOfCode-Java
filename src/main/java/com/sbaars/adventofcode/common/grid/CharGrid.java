package com.sbaars.adventofcode.common.grid;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CharGrid implements Grid {
  char[][] grid;

  public CharGrid(char[][] grid){
    this.grid = grid;
  }

  public int countChar(char...c){
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate(){
    return Arrays.stream(grid).map(String::new).flatMapToInt(String::chars);
  }
}
