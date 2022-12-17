package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static java.lang.Math.toIntExact;

public class Day17 extends Day2022 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    Day d = new Day17();
    System.out.println(d.part2());
//    d.downloadIfNotDownloaded();
//    d.downloadExample();
//    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
//    long rocks = 1709;
//    long height = 2642;
//    while(rocks<1000000000000L){
//      rocks +=1725;
//      height+=2694;
//    }
//    System.out.println(rocks +", "+height);
//    System.out.println((1561739130578L-187L));
  }

  public record State(long height, long fallenRocks, boolean cycleReset) {}

  @Override
  public Object part1() {
    return simulateShapeMoves(2022, s -> false).height;
  }

  private State simulateShapeMoves(long iterations, Predicate<State> exitCondition) {
    InfiniteGrid[] shapes = new InfiniteGrid[]{
            new InfiniteGrid(new char[][]{"####".toCharArray()}),
            new InfiniteGrid(new HashMap<>(Map.of(new Loc(1, 0), '#', new Loc(0, 1), '#', new Loc(1, 1), '#', new Loc(2, 1), '#', new Loc(1, 2), '#'))),
            new InfiniteGrid(new HashMap<>(Map.of(new Loc(2, 0), '#', new Loc(0, 2), '#', new Loc(1, 2), '#', new Loc(2, 2), '#', new Loc(2, 1), '#'))),
            new InfiniteGrid(new char[][]{{'#'}, {'#'}, {'#'}, {'#'}}),
            new InfiniteGrid(new char[][]{{'#', '#'}, {'#', '#'}})
    };
    InfiniteGrid g = new InfiniteGrid(new char[][]{"+-------+".toCharArray()});
    addWall(g, 4);
    char[] chars = day().trim().toCharArray();
    int i = 0;
    int shapeIndex = 0;
    long highest = 0;
    InfiniteGrid s = shapes[shapeIndex].move(3, highest - 4);
    for(int fallenRocks = 0; fallenRocks<iterations; i++) {
      if(i>=chars.length) i = 0;
      State state = new State(Math.abs(highest), fallenRocks, i==0);
      if(exitCondition.test(state)) {
        return state;
      }

      char c = chars[i];

      if(c == '>') {
        InfiniteGrid moved = s.move(1, 0);
        if(g.canPlace(moved)) {
          s = moved;
        }
      } else if(c == '<') {
        InfiniteGrid moved = s.move(-1, 0);
        if(g.canPlace(moved)) {
          s = moved;
        }
      }

      InfiniteGrid moved = s.move(0, 1);
      if(g.canPlace(moved)) {
        s = moved;
      } else {
        g.place(s);
        shapeIndex = (shapeIndex + 1) % shapes.length;
        long oldHeighest = highest;
        highest = Math.min(s.minY(), highest);
        addWall(g, toIntExact((oldHeighest - highest) + shapes[shapeIndex].height()));
        s = shapes[shapeIndex].move(3, highest - 3 - shapes[shapeIndex].height());
        fallenRocks++;
      }
    }
    return new State(Math.abs(highest), iterations, i==0);
  }

  private void addWall(InfiniteGrid g, int n) {
    long lowestY = g.minY()-1;
    for(int i = 0; i<n; i++){
      g.set(new Loc(0, lowestY-i), '|');
      g.set(new Loc(8, lowestY-i), '|');
    }
  }

  @Override
  public Object part2() {
    long iterations = 1000000000000L;
    State cycleStart = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks != 0);
    State nextCycle = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks > cycleStart.fallenRocks);
    long rocks = cycleStart.fallenRocks;
    long height = cycleStart.height;
    while(rocks<iterations){
      rocks += nextCycle.fallenRocks - cycleStart.fallenRocks;
      height += nextCycle.height - cycleStart.height;
    }
    long overshoot = rocks - iterations;
    State atOvershoot = simulateShapeMoves(iterations, s -> s.fallenRocks == cycleStart.fallenRocks - overshoot);
    return height - (cycleStart.height - atOvershoot.height);
//    System.out.println(rocks +", "+height);
//    System.out.println((1561739130578L-187L));
//    InfiniteGrid[] shapes = new InfiniteGrid[]{
//            new InfiniteGrid(new char[][]{"####".toCharArray()}),
//            new InfiniteGrid(new HashMap<>(Map.of(new Loc(1, 0), '#', new Loc(0, 1), '#', new Loc(1, 1), '#', new Loc(2, 1), '#', new Loc(1, 2), '#'))),
//            new InfiniteGrid(new HashMap<>(Map.of(new Loc(2, 0), '#', new Loc(0, 2), '#', new Loc(1, 2), '#', new Loc(2, 2), '#', new Loc(2, 1), '#'))),
//            new InfiniteGrid(new char[][]{{'#'}, {'#'}, {'#'}, {'#'}}),
//            new InfiniteGrid(new char[][]{{'#', '#'}, {'#', '#'}})
//    };
//    InfiniteGrid g = new InfiniteGrid(new char[][]{"+-------+".toCharArray()});
//    addWall(g, 4);
//    char[] chars = day().trim().toCharArray();
//    int i = 0;
//    int shapeIndex = 0;
//    long highest = 0;
//    InfiniteGrid s = shapes[shapeIndex].move(3, highest - 4);
//    long it = 0;
//    long rockStart = 0, rockStep = 0, heightStart = 0, heightStep = 0,
//    for(long fallenRocks = 0; fallenRocks<1000000000000L; i++) {
//
//      if(i>=chars.length) i = 0;
//      if(/*0 == (fallenRocks-1709) % 1725 */ fallenRocks == 5159-109 || fallenRocks == 3434-109) {
//        System.out.println("i: "+i);
//        System.out.println("shape: "+shapeIndex);
//        System.out.println("highest: "+Math.abs(highest));
//        System.out.println("fallen: "+fallenRocks);
//      }
//      if(i == 1) {
//        long rocks = 1709;
//        long height = 2642;
//        while(rocks<1000000000000L){
//          rocks +=1725;
//          height+=2694;
//        }
//        System.out.println(rocks +", "+height);
//        System.out.println((1561739130578L-187L));
//      }
//      char c = chars[i];
//
//      if(c == '>') {
//        InfiniteGrid moved = s.move(1, 0);
//        if(g.canPlace(moved)) {
//          s = moved;
//        }
//      } else if(c == '<') {
//        InfiniteGrid moved = s.move(-1, 0);
//        if(g.canPlace(moved)) {
//          s = moved;
//        }
//      }
//
//      InfiniteGrid moved = s.move(0, 1);
//      if(g.canPlace(moved)) {
//        s = moved;
//      } else {
////        i--;
//        g.place(s);
//        shapeIndex = (shapeIndex + 1) % shapes.length;
//        long oldHeighest = highest;
//        highest = Math.min(s.minY(), highest);
//        addWall(g, toIntExact((oldHeighest - highest) + shapes[shapeIndex].height()));
//        s = shapes[shapeIndex].move(3, highest - 3 - shapes[shapeIndex].height());
//        fallenRocks++;
//      }
//
////      InfiniteGrid tog = new InfiniteGrid();
////      tog.place(g);
////      tog.place(s);
////      System.out.println(tog);
//      it++;
//    }
//    return Math.abs(highest);
  }
}
