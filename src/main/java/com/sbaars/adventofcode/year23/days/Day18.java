package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.List;

import static com.sbaars.adventofcode.common.Direction.*;
import static com.sbaars.adventofcode.util.AoCUtils.connectedPairs;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day18 extends Day2023 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  public record Dig(Direction dir, int n, long part2N, Direction part2Dir) {
    public Dig(char dir, int n, String hex) {
      this(Direction.getByDirCode(dir), n, Long.parseLong(hex.substring(0, hex.length() - 1), 16), hex.charAt(hex.length() - 1) == '0' ? EAST : hex.charAt(hex.length() - 1) == '1' ? SOUTH : hex.charAt(hex.length() - 1) == '2' ? WEST : NORTH);
    }
  }

  @Override
  public Object part1() {
    var in = input();
    Loc start = new Loc(0, 0);
    InfiniteGrid grid = new InfiniteGrid();
    for (Dig dig : in) {
      Loc end = dig.dir.move(start, dig.n);
      grid.draw(start, end, '#');
      start = end;
    }
    grid.floodFill(new Loc(1, 1), c -> c != '#').forEach(l -> grid.set(l, '#'));
    return grid.grid.size();
  }

  @Override
  public Object part2() {
    var in = input();
    Loc start = new Loc(0, 0);
    List<Loc> route = new ArrayList<>();
    long outline = 0;
    for (Dig dig : in) {
      Loc end = dig.part2Dir.move(start, dig.part2N);
      outline += start.distance(end);
      route.add(end);
      start = end;
    }
    return outline / 2 + connectedPairs(route).mapToLong(p -> (p.a().y + p.b().y) * (p.a().x - p.b().x)).sum() / 2 + 1;
  }

  private List<Dig> input() {
    return dayStream().map(s -> readString(s, "%c %i (#%s)", Dig.class)).toList();
  }
}
