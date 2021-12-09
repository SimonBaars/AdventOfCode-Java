package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.Direction.fourDirections;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Day9 extends Day2021 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) throws IOException {
    new Day9().printParts();
    System.in.read();
//    new Day9().submitPart1();
    new Day9().submitPart2();
  }

  @Override
  public Object part1() {
    var in = new NumGrid(day(), "\n", "").grid;
    long sum = 0;
    for(int i = 0; i<in.length; i++){
      for(int j = 0; j<in[i].length; j++){
        final int x = i, y = j;
        if(Arrays.stream(fourDirections()).map(d -> d.getInGrid(in, new Point(x,y), Integer.MAX_VALUE)).allMatch(n -> n > in[x][y])){
          sum+=1+in[x][y];
        }
      }
    }
    return sum;
  }

  @Override
  public Object part2() {
    var in = new NumGrid(day(), "\n", "").grid;
    int[] b = new int[3];
    for(int i = 0; i<in.length; i++){
      for(int j = 0; j<in[i].length; j++){
        var loc = new Point(i, j);
        if(isLowPoint(in, loc)){
          var basin = findBasins(new HashSet<>(), in, loc);
          if(b[0] <= b[1] && b[0] <= b[2] && basin > b[0]){
            b[0] = basin;
          } else if(b[1] <= b[0] && b[1] <= b[2] && basin > b[1]){
            b[1] = basin;
          } else if(b[2] <= b[1] && b[2] <= b[0] && basin > b[2]){
            b[2] = basin;
          }
        }
      }
    }
    return IntStream.of(b).reduce((c, d) -> c*d).getAsInt();
  }

  private boolean isLowPoint(long[][] in, Point loc) {
    return Arrays.stream(fourDirections()).map(d -> d.getInGrid(in, loc, Integer.MAX_VALUE)).allMatch(n -> n > in[loc.x][loc.y]);
  }

  private boolean isLowPoint(long[][] in, Point loc, Set<Point> ex) {
    if(Arrays.stream(fourDirections()).filter(d -> !ex.contains(d.move(loc))).map(d -> d.getInGrid(in, loc, Integer.MAX_VALUE)).allMatch(e -> e == Integer.MAX_VALUE)){
      return false;
    }
    return Arrays.stream(fourDirections()).filter(d -> !ex.contains(d.move(loc))).map(d -> d.getInGrid(in, loc, Integer.MAX_VALUE)).allMatch(n -> n > in[loc.x][loc.y]);
  }

  private int findBasins(Set<Point> illegal, long[][] in, Point loc) {
    illegal.add(loc);
    int amount = 1;
    for(Direction d : fourDirections()){
      Point p = d.move(loc);
      if(d.getInGrid(in, loc, -1) != -1 && !illegal.contains(p) && isLowPoint(in, p, illegal)){
        amount += findBasins(illegal, in, p);
      }
    }
    return amount;
  }
}
