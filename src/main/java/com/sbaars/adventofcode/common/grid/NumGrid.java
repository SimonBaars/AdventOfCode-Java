package com.sbaars.adventofcode.common.grid;

import com.sbaars.adventofcode.common.Direction;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class NumGrid implements Grid {
  public long[][] grid;

  public NumGrid(String stringToParse, String lineDelimiter, String itemDelimiter) {
    this.grid = numGrid(stringToParse, lineDelimiter, itemDelimiter);
  }

  public NumGrid(String stringToParse) {
    this(stringToParse, "\n", " ");
  }

  public NumGrid(long[][] grid) {
    this.grid = grid;
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

  public NumGrid mirrorX(){
    long[][] newGrid = new long[sizeX()][sizeY()];
    for(int i = 0; i<sizeX(); i++) {
      for(int j = 0; j<sizeY(); j++) {
        newGrid[i][sizeY()-j-1] = grid[i][j];
      }
    }
    grid = newGrid;
    return this;
  }

  public NumGrid mirrorY(){
    long[][] newGrid = new long[sizeX()][sizeY()];
    for(int i = 0; i<sizeX(); i++) {
      for(int j = 0; j<sizeY(); j++) {
        newGrid[sizeX()-i-1][j] = grid[i][j];
      }
    }
    grid = newGrid;
    return this;
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

  public Stream<Point> stream() {
    return IntStream.range(0, grid.length).boxed().flatMap(x -> IntStream.range(0, grid[x].length).mapToObj(y -> new Point(x, y)));
  }

  @Override
  public String toString(){
    StringBuilder res = new StringBuilder();
    for(long[] nums : grid) res.append(LongStream.of(nums).mapToObj(Long::toString).collect(Collectors.joining(","))).append("\n");
    return res.toString();
  }

  public long get(Point p) {
    if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
      return grid[p.x][p.y];
    }
    return -1;
  }

  /**
   * The get method doesn't properly work with x and y positions, it assumes x=y and y=x. This one fixes that.
   * Why not fix `get` you ask? Because I'm afraid it'll break earlier days. That's why.
   */
  public long at(Point p) {
    if (p.x >= 0 && p.x < grid[0].length && p.y >= 0 && p.y < grid.length) {
      return grid[p.y][p.x];
    }
    return -1;
  }

  public long at(Point p, int or) {
    if (p.x >= 0 && p.x < grid[0].length && p.y >= 0 && p.y < grid.length) {
      return grid[p.y][p.x];
    }
    return or;
  }

  public int sizeX(){
    return grid.length;
  }

  public int sizeY(){
    return grid[0].length;
  }

  public void set(Point p, long i) {
    if(get(p) == -1) throw new IllegalStateException("Trying to write to coordinate outside of grid: "+p+", "+i);
    grid[p.x][p.y] = i;
  }

  public void increment(Point p) {
    increment(p, 1);
  }

  public void increment(Point p, int n) {
    set(p, get(p)+n);
  }

  public Stream<Point> streamDirs(Point p) {
    return Arrays.stream(Direction.fourDirections()).map(d -> d.move(p));
  }

  public Point find(long l) {
    return stream().filter(p -> get(p) == l).findFirst().get();
  }

  public Stream<Point> findAll(long l) {
    return stream().filter(p -> get(p) == l);
  }
}
