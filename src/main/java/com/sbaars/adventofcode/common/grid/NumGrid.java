package com.sbaars.adventofcode.common.grid;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumGrid implements Grid {
  public long[][] grid;

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

  public long count(long...n) {
    return iterateLong().filter(e -> LongStream.of(n).anyMatch(i -> i == e)).count();
  }

  public long countExcept(long...n) {
    return Math.toIntExact(iterateLong().filter(e -> LongStream.of(n).noneMatch(i -> i == e)).count());
  }

  public long sum() {
    return iterateLong().sum();
  }

  public long sum(long...n) {
    return iterateLong().filter(e -> LongStream.of(n).anyMatch(i -> i == e)).sum();
  }

  public long sumExcept(long...n) {
    return iterateLong().filter(e -> LongStream.of(n).noneMatch(i -> i == e)).sum();
  }

  public boolean replace(long orig, long replacement){
    boolean changed = false;
    for(int i = 0; i< grid.length; i++){
      for(int j = 0; j< grid[i].length; j++){
        if(grid[i][j] == orig){
          grid[i][j] = replacement;
          changed = true;
        }
      }
    }
    return changed;
  }

  public IntStream iterate() {
    return LongStream.range(0, grid.length).flatMap(i -> LongStream.of(grid[Math.toIntExact(i)])).mapToInt(Math::toIntExact);
  }

  public LongStream iterateLong() {
    return LongStream.range(0, grid.length).flatMap(i -> LongStream.of(grid[Math.toIntExact(i)]));
  }
}
