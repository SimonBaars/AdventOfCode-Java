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
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.util.AOCUtils.alternating;
import static java.util.stream.IntStream.range;

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

  List<Range> squares = List.of(
          square(0, 3),
          square(0, 2),
          square(1, 2),
          square(1, 1),
          square(1, 0),
          square(2, 0)
  );
  Map<Location, Location> sides = Map.of(
          new Location(5, EAST), new Location(2, EAST),
          new Location(5, SOUTH), new Location(3, EAST),
          new Location(5, NORTH), new Location(0, SOUTH),
          new Location(3, WEST), new Location(1, NORTH),
          new Location(2, SOUTH), new Location(0, EAST),
          new Location(4, NORTH), new Location(0, WEST),
          new Location(4, WEST), new Location(1, WEST)
  ).entrySet().stream() // This stream makes the map bidirectional
          .flatMap(e -> Stream.of(Pair.pair(e.getKey(), e.getValue()), Pair.pair(e.getValue(), e.getKey())))
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
    Me me = alternating(in[1].trim()).reduce(
            new Me(g.grid.keySet().stream().filter(l -> g.getChar(l) == '.' && l.y == 0).findFirst().get(), EAST),
            (dir, m) -> new Me(m.l, m.d.turn(dir.equals("R"))),
            (steps, m) -> walk(cube, g, m, steps)
    );
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
    int oldSquareIndex = range(0, squares.size()).filter(i -> squares.get(i).inRange(me.l)).findFirst().getAsInt();
    var targetSide = sides.get(new Location(oldSquareIndex, me.d));
    Range oldSquare = squares.get(oldSquareIndex);
    Range newSquare = squares.get(targetSide.c);
    long diff =  me.d.diagonal() ? me.l.x - oldSquare.start.x : me.l.y - oldSquare.start.y;
    long newX = targetSide.d == WEST ? newSquare.start.x : targetSide.d == EAST ? newSquare.end.x : targetSide.d == me.d ? newSquare.end.x - diff : newSquare.start.x + diff;
    long newY = targetSide.d == NORTH ? newSquare.start.y : targetSide.d == SOUTH ? newSquare.end.y : targetSide.d == me.d ? newSquare.end.y - diff : newSquare.start.y + diff;
    return new Me(new Loc(newX, newY), targetSide.d.opposite());
  }

  private Range square(int x, int y) {
    Loc a = new Loc(x * CUBE_SIZE, y * CUBE_SIZE);
    return new Range(a, new Loc(a.x+CUBE_SIZE-1, a.y+CUBE_SIZE-1));
  }
}
