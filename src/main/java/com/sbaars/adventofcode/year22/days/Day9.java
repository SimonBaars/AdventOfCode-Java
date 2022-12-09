package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day9 extends Day2022 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    Day d = new Day9();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Move(String dir, long n){}

  @Override
  public Object part1() {
    List<Move> moves = dayStream().map(s -> readString(s, "%s %n", Move.class)).toList();
    Point p1 = new Point();
    Point p2 = new Point();
    HashSet<Point> vis = new HashSet<>();
    vis.add(p2);
    for(Move m : moves) {
      Direction dir = Direction.getByDirCode(m.dir.charAt(0));
      for(int i = 0; i<m.n; i++) {
        p1 = dir.move(p1);
        Point p12 = p1, p22 = p2;
        if(Arrays.stream(Direction.values()).noneMatch(d -> d.move(p22).equals(p12))) {
          if(p1.x > p2.x && p1.y == p2.y) {
            p2 = Direction.EAST.move(p2);
          } else if(p1.x < p2.x && p1.y == p2.y){
            p2 = Direction.WEST.move(p2);
          } else if(p1.x == p2.x && p1.y > p2.y) {
            p2 = Direction.SOUTH.move(p2);
          } else if(p1.x == p2.x && p1.y < p2.y){
            p2 = Direction.NORTH.move(p2);
          } else if(p1.x > p2.x && p1.y > p2.y) {
            p2 = Direction.SOUTHEAST.move(p2);
          } else if(p1.x < p2.x && p1.y < p2.y){
            p2 = Direction.NORTHWEST.move(p2);
          } else if(p1.x < p2.x && p1.y > p2.y) {
            p2 = Direction.SOUTHWEST.move(p2);
          } else if(p1.x > p2.x && p1.y < p2.y){
            p2 = Direction.NORTHEAST.move(p2);
          } else throw new IllegalStateException();
        }
        vis.add(p2);
      }
    }
    return vis.size();
  }

  @Override
  public Object part2() {
    List<Move> moves = dayStream().map(s -> readString(s, "%s %n", Move.class)).toList();
    Point p1 = new Point();
    List<Point> p2 = new java.util.ArrayList<>(IntStream.range(0, 9).mapToObj(i -> new Point()).toList());
    HashSet<Point> vis = new HashSet<>();
    vis.add(p1);

    for(Move m : moves) {
      Direction dir = Direction.getByDirCode(m.dir.charAt(0));
      for(int i = 0; i<m.n; i++) {
//        NumGrid g = new NumGrid(new long[6][6]);
        p1 = dir.move(p1);
//        g.set(new Point(Math.abs(p1.x), Math.abs(p1.y)), 11);
        for(int j = 0; j<p2.size(); j++) {
          Point p12 = j == 0 ? p1 : p2.get(j - 1), p22 = p2.get(j);
          Point p23 = p22;
          if (Arrays.stream(Direction.values()).noneMatch(d -> d.move(p23).equals(p12))) {
            if (p12.x > p22.x && p12.y == p22.y) {
              p22 = Direction.EAST.move(p22);
            } else if (p12.x < p22.x && p12.y == p22.y) {
              p22 = Direction.WEST.move(p22);
            } else if (p12.x == p22.x && p12.y > p22.y) {
              p22 = Direction.SOUTH.move(p22);
            } else if (p12.x == p22.x && p12.y < p22.y) {
              p22 = Direction.NORTH.move(p22);
            } else if (p12.x > p22.x && p12.y > p22.y) {
              p22 = Direction.SOUTHEAST.move(p22);
            } else if (p12.x < p22.x && p12.y < p22.y) {
              p22 = Direction.NORTHWEST.move(p22);
            } else if (p12.x < p22.x && p12.y > p22.y) {
              p22 = Direction.SOUTHWEST.move(p22);
            } else if (p12.x > p22.x && p12.y < p22.y) {
              p22 = Direction.NORTHEAST.move(p22);
            } else throw new IllegalStateException();
          }
          p2.set(j, p22);
          if(j == p2.size()-1) vis.add(p22);
//          g.set(new Point(Math.abs(p22.x), Math.abs(p22.y)), j+1);
        }
//        System.out.println(g);
      }
    }
    return vis.size();
  }
}
