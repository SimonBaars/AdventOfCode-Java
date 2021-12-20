package com.sbaars.adventofcode.common.grid;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CharGrid implements Grid {
  public char[][] grid;

  public CharGrid(char[][] grid) {
    this.grid = grid;
  }

  public CharGrid(String s) {
    grid = Arrays.stream(s.split("\n")).map(String::toCharArray).toArray(char[][]::new);
  }

  public int countChar(char... c) {
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate() {
    return Arrays.stream(grid).map(String::new).flatMapToInt(String::chars);
  }
}
