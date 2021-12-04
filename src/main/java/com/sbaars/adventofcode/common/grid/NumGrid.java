package com.sbaars.adventofcode.common.grid;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumGrid implements Grid {
  long[][] grid;

  public NumGrid(String stringToParse, String lineDelimiter, String itemDelimiter) {
    this.grid = numGrid(stringToParse, lineDelimiter, itemDelimiter);
  }

  public NumGrid(String stringToParse) {
    this.grid = numGrid(stringToParse, "\n", " ");
  }

  private long[][] numGrid(String str, String lineDelimiter, String itemDelimiter) {
    String[] lines = str.split(lineDelimiter);
    long[][] res = new long[lines.length][];
    for(int i = 0; i<lines.length; i++){
      res[i] = Arrays.stream(lines[i].split(itemDelimiter)).map(String::trim).filter(e -> !e.isEmpty()).mapToLong(Long::parseLong).toArray();
    }
    return res;
  }

  public int countChar(char... c) {
    return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
  }

  public IntStream iterate() {
    return LongStream.range(0, grid.length).flatMap(i -> LongStream.of(grid[Math.toIntExact(i)])).mapToInt(Math::toIntExact);
  }
}
