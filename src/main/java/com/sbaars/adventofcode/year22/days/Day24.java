package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day24 extends Day2022 {

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    Day24 d = new Day24();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record State(Loc pos) {
    public long close(Loc dest) {
      return pos.distance(dest);
    }
  }

  public record Blizzard(Loc l, Direction dir) {}

  @Override
  public Object part1() {
    Set<Loc> states = new HashSet<>();
    InfiniteGrid in = new InfiniteGrid(dayGrid(), '.');
    List<Blizzard> blizzards = in.grid.entrySet().stream().filter(e -> List.of('^', 'v', '>', '<').contains(e.getValue())).map(e -> new Blizzard(e.getKey(), getDir(e.getValue()))).toList();
    new HashSet<>(in.grid.keySet()).stream().filter(e -> in.get(e).map(f -> List.of('^', 'v', '>', '<').contains(f)).orElse(false)).forEach(e -> in.grid.remove(e));
    states.add(new Loc(1, 0));
    Loc dest = new Loc(in.maxX()-1, in.maxY());
    for(long i = 0; true; i++) {
      InfiniteGrid g = new InfiniteGrid(in.grid);
      blizzards = blizzards.stream()
              .map(b -> new Blizzard(b.dir.move(b.l), b.dir))
              .map(b -> in.get(b.l).isPresent() ? new Blizzard(new Loc(b.dir == WEST ? in.maxX()-1 : b.dir == EAST ? 1 : b.l.x, b.dir == NORTH ? in.maxY()-1 : b.dir == SOUTH ? 1 : b.l.y), b.dir) : b)
              .toList();
      blizzards.forEach(b -> g.set(b.l, 'X'));
      states = states.stream().flatMap(s -> Direction.five().map(d -> d.move(s)).filter(l -> l.x>=0 && l.y>=0 && !g.contains(l))).collect(Collectors.toSet());
      if(states.contains(dest)) {
        return i+1;
      }
    }
  }

  private Direction getDir(char c){
    return switch(c) {
      case '^' -> NORTH;
      case 'v' -> SOUTH;
      case '>' -> EAST;
      case '<' -> WEST;
      default -> throw new IllegalStateException("Unknown char: "+c);
    };
  }

  @Override
  public Object part2() {
    Set<Loc> states = new HashSet<>();
    InfiniteGrid in = new InfiniteGrid(dayGrid(), '.');
    List<Blizzard> blizzards = in.grid.entrySet().stream().filter(e -> List.of('^', 'v', '>', '<').contains(e.getValue())).map(e -> new Blizzard(e.getKey(), getDir(e.getValue()))).toList();
    new HashSet<>(in.grid.keySet()).stream().filter(e -> in.get(e).map(f -> List.of('^', 'v', '>', '<').contains(f)).orElse(false)).forEach(e -> in.grid.remove(e));
    states.add(new Loc(1, 0));
    Loc dest = new Loc(in.maxX()-1, in.maxY());
    boolean realEnd = false;
    for(long i = 0; true; i++) {
      InfiniteGrid g = new InfiniteGrid(in.grid);
      blizzards = blizzards.stream()
              .map(b -> new Blizzard(b.dir.move(b.l), b.dir))
              .map(b -> in.get(b.l).isPresent() ? new Blizzard(new Loc(b.dir == WEST ? in.maxX()-1 : b.dir == EAST ? 1 : b.l.x, b.dir == NORTH ? in.maxY()-1 : b.dir == SOUTH ? 1 : b.l.y), b.dir) : b)
              .toList();
      blizzards.forEach(b -> g.set(b.l, 'X'));
      states = states.stream().flatMap(s -> Direction.five().map(d -> d.move(s)).filter(l -> l.x>=0 && l.y>=0 && !g.contains(l))).collect(Collectors.toSet());
      if(states.contains(dest)) {
        states = Set.of(dest);
        if(dest.equals(new Loc(1, 0))) {
          dest = new Loc(in.maxX()-1, in.maxY());
          realEnd = true;
        } else if (!realEnd) {
          dest = new Loc(1, 0);
        } else {
          return i+1;
        }
      }
    }
  }
}
