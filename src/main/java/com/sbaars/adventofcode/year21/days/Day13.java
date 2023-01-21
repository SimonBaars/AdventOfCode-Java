package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.grid.Coordinates;
import com.sbaars.adventofcode.year21.Day2021;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.toIntExact;

public class Day13 extends Day2021 {
  private final Coordinates dots;
  private final List<Fold> folds;

  public Day13() {
    super(13);
    var in = day().split("\n\n");
    dots = new Coordinates(in[0]);
    folds = Arrays.stream(in[1].split("\n")).map(e -> readString(e, "fold along %s=%n", Fold.class)).toList();
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  @Override
  public Object part1() {
    return dots.stream().map(e -> fold(folds.get(0), e)).distinct().count();
  }

  public record Fold (String axis, long n){}

  @Override
  public Object part2() {
    var d = dots;
    for(Fold f : folds) {
      d = d.transform(e -> fold(f, e));
    }
    return "\n"+d.toGrid().toString().replace(",", "").replace("0", " ");
  }

  private Point fold(Fold f, Point e) {
    if(f.axis.equals("y")){
      return e.y > f.n ? new Point(e.x, toIntExact(f.n - (e.y - f.n))) : e;
    }
    return e.x > f.n ? new Point(toIntExact(f.n - (e.x - f.n)), e.y) : e;
  }
}
