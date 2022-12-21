package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Long.MIN_VALUE;
import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day17 extends Day2021 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  @Override
  public Object part1() {
    return getSol().max().getAsLong();
  }

  private LongStream getSol() {
    var in = readString(day().trim(), "target area: x=%n..%n, y=%n..%n", Target.class);
    var area = new Area(new Point(toIntExact(in.xStart), toIntExact(in.yStart)), new Point(toIntExact(in.xEnd), toIntExact(in.yEnd)));
    return IntStream.range(-200, 300).boxed().flatMap(x -> IntStream.range(-200, 300).mapToObj(y -> new Point(x, y))).mapToLong(p -> simulateSteps(area, p));
  }

  public long simulateSteps(Area target, Point p){
    Point curr = new Point(0,0);
    long highest = 0;
    while(curr.y>target.topLeft.y && !target.inArea(curr)) {
      curr = new Point(curr.x + p.x, curr.y + p.y);
      p = new Point(p.x > 0 ? p.x - 1 : (p.x < 0 ? p.x - 1 : p.x), p.y - 1);
      if(curr.y > highest) highest = curr.y;
      if(curr.x == 0 && (curr.y < target.topLeft.y || curr.y > target.bottomRight.y)) break;
    }
    if(target.inArea(curr)){
      return highest;
    }
    return MIN_VALUE;
  }

  @Override
  public Object part2() {
    return getSol().filter(e -> e!=MIN_VALUE).count();
  }

  public record Target(long xStart, long xEnd, long yStart, long yEnd){}

  public record Area(Point topLeft, Point bottomRight){
    public boolean inArea(Point p){
      return p.x>=topLeft.x && p.y >= topLeft.y && p.x <=bottomRight.x && p.y <= bottomRight.y;
    }
  }
}
