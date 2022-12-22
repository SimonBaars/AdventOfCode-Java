package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day22 extends Day2022 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) throws IOException {
    new Day22().printParts();
  }

  public record Me (Loc l, Direction d) {}
  public record Location(int c, Direction d) {}

  Map<Integer, Range> cubes = Map.of(
          5, new Range(50, 0, 99, 49)/*5*/,
          6, new Range(100, 0, 149, 49)/*6*/,
          4, new Range(50, 50, 99, 99)/*4*/,
          3, new Range(50, 100, 99, 149)/*3*/,
          2, new Range(0, 100, 49, 149)/*2*/,
          1, new Range(0, 150, 49, 199)/*1*/
  );
  Map<Location, Location> todo = Map.of(
          new Location(6, EAST), new Location(3, EAST), // A
          new Location(6, SOUTH), new Location(4, EAST), // B
          new Location(6, NORTH), new Location(1, SOUTH), // C
          new Location(4, WEST), new Location(2, NORTH), // D
          new Location(3, SOUTH), new Location(1, EAST), // E
          new Location(5, NORTH), new Location(1, WEST), // F
          new Location(5, WEST), new Location(2, WEST)  // G
  ).entrySet().stream()
          .flatMap(e -> Stream.of(Pair.of(e.getKey(), e.getValue()), Pair.of(e.getValue(), e.getKey())))
          .collect(Collectors.toMap(Pair::a, Pair::b));

  @Override
  public Object part1() {
    return solve(false);
  }

  @Override
  public Object part2() {
    return solve(true);
  }

  public long solve (boolean cube) {
    String[] in = day().split("\n\n");
    CharGrid grid = new CharGrid(in[0]);
    InfiniteGrid g = new InfiniteGrid(grid.grid);
    String instr = in[1].trim();
    Me me = new Me(g.grid.keySet().stream().filter(l -> grid.get(l) == '.' && l.y == 0).findFirst().get(), EAST);
    while(!instr.isEmpty()) {
      if(instr.charAt(0) == 'L' || instr.charAt(0) == 'R') {
        me = new Me(me.l, me.d.turn(instr.charAt(0) == 'R'));
        instr = instr.substring(1);
      } else {
        int i;
        for(i=0; i<instr.length() && Character.isDigit(instr.charAt(i)); i++);
        int n = Integer.parseInt(instr.substring(0, i));
        instr = instr.substring(i);
        for(i = 0; i<n; i++) {
          Loc newLoc = me.d.move(me.l);
          var atPos = g.get(newLoc);
          if(atPos.isPresent() && atPos.get() == '.') {
            me = new Me(newLoc, me.d);
          } else if(!atPos.isPresent()) {
            Me newMe = cube ? moveCubic(me) : move(g, me);
            if(g.get(newMe.l).get() == '.') {
              me = newMe;
            }
          }
        }
      }
    }
    return (1000 * (me.l.y+1)) + (4 * (me.l.x + 1)) + me.d.ordinal() - 1 + (me.d == NORTH ? 4 : 0);
  }

  public Me move(InfiniteGrid g, Me me) {
    Direction opposite = me.d.opposite();
    Loc newLoc = me.l;
    for(; g.get(newLoc).isPresent(); newLoc = opposite.move(newLoc));
    newLoc = me.d.move(newLoc);
    return new Me(newLoc, me.d);
  }

  public Me moveCubic(Me me) {
    int cube = cubes.entrySet().stream().filter(e -> e.getValue().inRange(me.l)).mapToInt(Map.Entry::getKey).findFirst().getAsInt();
    var tele = todo.get(new Location(cube, me.d));
    Range oldCube = cubes.get(cube);
    Range newCube = cubes.get(tele.c);
    long diff =  me.d.diagonal() ? me.l.x - oldCube.start.x : me.l.y - oldCube.start.y;
    long newX = tele.d == WEST ? newCube.start.x : tele.d == EAST ? newCube.end.x : tele.d == me.d ? newCube.end.x - diff : newCube.start.x + diff;
    long newY = tele.d == NORTH ? newCube.start.y : tele.d == SOUTH ? newCube.end.y : tele.d == me.d ? newCube.end.y - diff : newCube.start.y + diff;
    return new Me(new Loc(newX, newY), tele.d.opposite());
  }
}
