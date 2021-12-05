package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Day5 extends Day2021 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts(1);
//    new Day5().submitPart1();
//    new Day5().submitPart2();
  }

  @Override
  public Object part1() {
    var in = dayStream().map(e -> readString(e, "%n,%n -> %n,%n", Coords.class)).toList();
    Set<Point> all = new HashSet<>();
    Set<Point> vis = new HashSet<>();
    for(Coords c : in) {
      if(c.x1 == c.x2){
        for(long y = Math.min(c.y1, c.y2); y<=Math.max(c.y1, c.y2); y++){
          var l = new Point(Math.toIntExact(c.x1), Math.toIntExact(y));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      } else if(c.y1 == c.y2){
        for(long x = Math.min(c.x1, c.x2); x<=Math.max(c.x1, c.x2); x++){
          var l = new Point(Math.toIntExact(x), Math.toIntExact(c.y1));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      }
    }
    return vis.size();
  }

  @Override
  public Object part2() {
    var in = dayStream().map(e -> readString(e, "%n,%n -> %n,%n", Coords.class)).toList();
    Set<Point> all = new HashSet<>();
    Set<Point> vis = new HashSet<>();
    for(Coords c : in) {
      if(c.x1 == c.x2){
        for(long y = Math.min(c.y1, c.y2); y<=Math.max(c.y1, c.y2); y++){
          var l = new Point(Math.toIntExact(c.x1), Math.toIntExact(y));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      } else if(c.y1 == c.y2){
        for(long x = Math.min(c.x1, c.x2); x<=Math.max(c.x1, c.x2); x++){
          var l = new Point(Math.toIntExact(x), Math.toIntExact(c.y1));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      } else if((c.x1 > c.x2 && c.y1 > c.y2) || (c.x1 < c.x2 && c.y1 < c.y2)){
        for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
          var l = new Point(Math.toIntExact(Math.min(c.x1, c.x2)+x), Math.toIntExact(Math.min(c.y1, c.y2)+x));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      }else if(c.x1 < c.x2 && c.y1 > c.y2){
        for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
          var l = new Point(Math.toIntExact(c.x1+x), Math.toIntExact(c.y1-x));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      }else if(c.x1 > c.x2 && c.y1 < c.y2){
        for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
          var l = new Point(Math.toIntExact(c.x1-x), Math.toIntExact(c.y1+x));
          if(!all.add(l)){
            vis.add(l);
          }
        }
      } else throw new IllegalStateException("not covered "+c);
    }
    return vis.size();
  }

  public record Coords(long x1, long y1, long x2, long y2) {}
}
