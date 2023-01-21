package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day24 extends Day2022 {

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  Map<Character, Direction> dirs = Map.of('^', NORTH, 'v', SOUTH, '>', EAST, '<', WEST);

  public record Blizzard(Loc l, Direction dir) {
  }

  @Override
  public Object part1() {
    return solution(false);
  }

  @Override
  public Object part2() {
    return solution(true);
  }

  private long solution(boolean part2) {
    Set<Loc> states = new HashSet<>();
    InfiniteGrid in = new InfiniteGrid(dayGrid(), '.');
    List<Blizzard> blizzards = in.grid.entrySet().stream()
        .filter(e -> dirs.containsKey(e.getValue()))
        .map(e -> new Blizzard(e.getKey(), dirs.get(e.getValue())))
        .toList();
    in.removeIf((l, c) -> dirs.containsKey(c));
    states.add(new Loc(1, 0));
    long sizex = in.maxX();
    long sizey = in.maxY();
    Loc dest = new Loc(sizex - 1, sizey);
    boolean realEnd = false;
    for (long i = 1; true; i++) {
      InfiniteGrid g = new InfiniteGrid(in);
      blizzards = blizzards.stream()
          .map(b -> new Blizzard(b.dir.move(b.l), b.dir))
          .map(b -> in.get(b.l).isPresent() ?
              new Blizzard(new Loc(b.dir == WEST ? sizex - 1 : b.dir == EAST ? 1 :
                  b.l.x, b.dir == NORTH ? sizey - 1 : b.dir == SOUTH ? 1 : b.l.y), b.dir) : b)
          .toList();
      blizzards.forEach(b -> g.set(b.l, 'X'));
      states = states.stream()
          .flatMap(s -> Direction.five().map(d -> d.move(s)).filter(l -> l.x >= 0 && l.y >= 0 && !g.contains(l)))
          .collect(Collectors.toSet());
      if (states.contains(dest)) {
        if (!part2 || realEnd) return i;
        states = Set.of(dest);
        if (dest.equals(new Loc(1, 0))) {
          dest = new Loc(sizex - 1, sizey);
          realEnd = true;
        } else {
          dest = new Loc(1, 0);
        }
      }
    }
  }
}
