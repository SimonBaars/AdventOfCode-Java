package com.sbaars.adventofcode.common.grid;

import com.sbaars.adventofcode.common.location.Loc;

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

  public CharGrid(char c, Loc size) {
    this.grid = new char[size.intY()][size.intX()];
    for(int y = 0; y<size.y; y++) {
      for(int x = 0; x<size.x; x++) {
        grid[y][x] = c;
      }
    }
  }

  public int countChar(char... c) {
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate() {
    return Arrays.stream(grid).map(String::new).flatMapToInt(String::chars);
  }

  public CharGrid copy() {
    return new CharGrid(Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CharGrid charGrid = (CharGrid) o;
    return Arrays.deepEquals(grid, charGrid.grid);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(grid);
  }

  @Override
  public String toString(){
    StringBuilder res = new StringBuilder();
    for(char[] nums : grid) res.append(new String(nums)).append("\n");
    return res.toString();
  }

  public char get(Loc p) {
    if (p.x >= 0 && p.x < grid[0].length && p.y >= 0 && p.y < grid.length) {
      return grid[p.intY()][p.intX()];
    }
    return 0;
  }

  public void set(Loc p, char i) {
    if(get(p) == 0) throw new IllegalStateException("Trying to write to coordinate outside of grid: "+p+", "+i);
    grid[p.intY()][p.intX()] = i;
  }

  public char[][] getGrid(){
    return grid;
  }
}
