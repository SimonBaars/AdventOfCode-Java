package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day13 extends Day2021 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
//    new Day13().submitPart1();
//    new Day13().submitPart2();
  }

  @Override
  public Object part1() {
    var in = day().split("\n\n");
    var dots = Arrays.stream(in[0].split("\n")).map(e -> e.split(",")).map(e -> new Point(Integer.parseInt(e[0]), Integer.parseInt(e[1]))).collect(Collectors.toSet());
    var folds = Arrays.stream(in[1].split("\n")).map(e -> readString(e, "fold along %s=%n", Fold.class)).toList();
    dots = dots.stream().map(e -> e.x>folds.get(0).n ? new Point(toIntExact(folds.get(0).n-(e.x-folds.get(0).n)), e.y) : e).collect(Collectors.toSet());
    return dots.size();
  }

  public record Fold (String axis, long n){}

  @Override
  public Object part2() {
    var in = day().split("\n\n");
    var dots = Arrays.stream(in[0].split("\n")).map(e -> e.split(",")).map(e -> new Point(Integer.parseInt(e[0]), Integer.parseInt(e[1]))).collect(Collectors.toSet());
    var folds = Arrays.stream(in[1].split("\n")).map(e -> readString(e, "fold along %s=%n", Fold.class)).toList();
    for(Fold f : folds) {
      dots = dots.stream().map(e -> fold(f, e)).collect(Collectors.toSet());
    }
    NumGrid g = new NumGrid(new long[dots.stream().mapToInt(e -> e.y).max().getAsInt()][dots.stream().mapToInt(e -> e.x).max().getAsInt()]);
    dots.stream().map(e -> new Point(e.y, e.x)).forEach(e -> g.set(e, 1L));
    return "\n"+g.toString().replace(",", "").replace("0", " ");
  }

  private Point fold(Fold f, Point e) {
    if(f.axis.equals("y")){
      return e.y > f.n ? new Point(e.x, toIntExact(f.n - (e.y - f.n))) : e;
    }
    return e.x > f.n ? new Point(toIntExact(f.n - (e.x - f.n)), e.y) : e;
  }
}
