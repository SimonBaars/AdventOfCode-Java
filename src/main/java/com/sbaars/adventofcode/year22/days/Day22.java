package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.util.AOCUtils.alternating;

public class Day22 extends Day2022 {
  private final int CUBE_SIZE = 50;

  public Day22() {
    super(22);
  }

  public static void main(String[] args) throws IOException {
    new Day22().printParts();
  }

  public record Me (Loc l, Direction d) {}
  public record Location(int c, Direction d) {}

  List<Range> cubes = List.of(cube(0, 3), cube(0, 2), cube(1, 2), cube(1, 1), cube(1, 0), cube(2, 0));
  Map<Location, Location> todo = Map.of(
          new Location(5, EAST), new Location(2, EAST), // A
          new Location(5, SOUTH), new Location(3, EAST), // B
          new Location(5, NORTH), new Location(0, SOUTH), // C
          new Location(3, WEST), new Location(1, NORTH), // D
          new Location(2, SOUTH), new Location(0, EAST), // E
          new Location(4, NORTH), new Location(0, WEST), // F
          new Location(4, WEST), new Location(1, WEST)  // G
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

  public long solve(boolean cube) {
    String[] in = day().split("\n\n");
    InfiniteGrid g = new InfiniteGrid(in[0]);
    var instr = alternating(in[1].trim());
    Me me = new Me(g.grid.keySet().stream().filter(l -> g.getChar(l) == '.' && l.y == 0).findFirst().get(), EAST);
    for(var instruction : instr) {
      if(instruction.isA()) {
        me = new Me(me.l, me.d.turn(instruction.getA().equals("R")));
      } else {
        me = walk(cube, g, me, instruction.getB());
      }
    }
    return (1000 * (me.l.y + 1)) + (4 * (me.l.x + 1)) + me.d.ordinal() - 1 + (me.d == NORTH ? 4 : 0);
  }

  private Me walk(boolean cube, InfiniteGrid g, Me me, long steps) {
    for(int i = 0; i<steps; i++) {
      Loc newLoc = me.d.move(me.l);
      var atPos = g.get(newLoc);
      if(atPos.isPresent() && atPos.get() == '.') {
        me = new Me(newLoc, me.d);
      } else if(atPos.isEmpty()) {
        Me oldMe = me;
        me = cube ? moveCubic(me) : move(g, me);
        if(g.get(me.l).get() != '.') {
          return oldMe;
        }
      }
    }
    return me;
  }

  private Me move(InfiniteGrid g, Me me) {
    Direction opposite = me.d.opposite();
    Loc newLoc = me.l;
    for(; g.get(newLoc).isPresent(); newLoc = opposite.move(newLoc));
    newLoc = me.d.move(newLoc);
    return new Me(newLoc, me.d);
  }

  private Me moveCubic(Me me) {
    int cube = IntStream.range(0, cubes.size()).filter(i -> cubes.get(i).inRange(me.l)).findFirst().getAsInt();
    var tele = todo.get(new Location(cube, me.d));
    Range oldCube = cubes.get(cube);
    Range newCube = cubes.get(tele.c);
    long diff =  me.d.diagonal() ? me.l.x - oldCube.start.x : me.l.y - oldCube.start.y;
    long newX = tele.d == WEST ? newCube.start.x : tele.d == EAST ? newCube.end.x : tele.d == me.d ? newCube.end.x - diff : newCube.start.x + diff;
    long newY = tele.d == NORTH ? newCube.start.y : tele.d == SOUTH ? newCube.end.y : tele.d == me.d ? newCube.end.y - diff : newCube.start.y + diff;
    return new Me(new Loc(newX, newY), tele.d.opposite());
  }

  private Range cube(int x, int y) {
    Loc a = new Loc(x * CUBE_SIZE, y * CUBE_SIZE);
    return new Range(a, new Loc(a.x+CUBE_SIZE-1, a.y+CUBE_SIZE-1));
  }
}
