package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Day11 extends Day2021 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) throws IOException {
    new Day11().printParts();
    System.in.read();
    new Day11().submitPart1();
//    new Day11().submitPart2();
  }

  @Override
  public Object part1() {
    var in = new NumGrid(day(), "\n", "");
    long flashes = 0;
    for(int i = 0; i<100; i++){
      flashes+=in.stream().mapToLong(e -> flash(in, e)).sum();
      in.stream().filter(p -> in.get(p) > 9).forEach(p -> in.set(p, 0));
    }
    return flashes;
  }

  private long flash(NumGrid in, Point e) {
    long n = 0;
    in.grid[e.x][e.y]++;
    if(in.get(e) == 10){
      n+=Arrays.stream(Direction.values()).map(d -> d.move(e)).filter(p -> in.get(p) != -1).mapToLong(p -> flash(in, p)).sum() + 1;
    }
    return n;
  }

  @Override
  public Object part2() {
    var in = new NumGrid(day(), "\n", "");
    for(int step = 1; true; step++){
      in.stream().forEach(e -> flash(in, e));
      in.stream().filter(p -> in.get(p) > 9).forEach(p -> in.set(p, 0));
      if(in.stream().mapToLong(in::get).allMatch(e -> e == 0L)){
        return step;
      }
    }
  }
}
