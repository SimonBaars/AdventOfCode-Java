package com.sbaars.adventofcode.common;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid {
  char[][] grid;

  public Grid(char[][] grid){
    this.grid = grid;
  }

  public int countChar(char...c){
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate(){
    return Arrays.stream(grid).map(String::new).flatMapToInt(String::chars);
  }
}
