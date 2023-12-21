package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.util.Solver;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.four;

public class Day21 extends Day2023 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  @Override
  public Object part1() {
//    this.example = 1;
    var grid = new InfiniteGrid(dayGrid());
    Builder<Set<Loc>> places = new Builder<>(HashSet::new);
    places.get().add(grid.findAll('S').findAny().get());
    for (int i = 0; i < 64; i++) {
      places.get().stream().flatMap(l -> four().map(d -> d.move(l))).filter(l -> grid.getChar(l) != '#').forEach(places.getNew()::add);
      places.refresh();
    }
    return places.get().size();
  }

  @Override
  public Object part2() {
    var grid = new InfiniteGrid(dayGrid());
    Loc start = grid.findAll('S').findAny().get();
    grid.repeat(3);
//    System.out.println(grid);
//    grid = new InfiniteGrid(grid.grid);
//    grid.repeat(1);
//    System.out.println(grid);
//    Builder<Set<Loc>> places = new Builder<>(HashSet::new);
//    places.get().add(start);
//    for (int i = 0; i < 10000; i++) {
//      places.get().stream().flatMap(l -> four().map(d -> d.move(l))).peek(l -> {
//        if (!grid.contains(l)) {
//          System.out.println("DIE");
//          throw new IllegalStateException();
//        }
//      }).filter(l -> grid.getChar(l) != '#').forEach(places.getNew()::add);
//      places.refresh();
//      System.out.println(places.get().size());
//    }
//    return places.get().size();
    return Solver.solve(Stream.iterate(new Pair<>(grid, new HashSet<>(List.of(start))), this::doTurn2), p -> p.b().size(), 26501365L);
  }

  int i = 1;

  public Pair<InfiniteGrid, Set<Loc>> doTurn(Pair<InfiniteGrid, Set<Loc>> places) {
    var set = new HashSet<Loc>();
    places.b().stream().flatMap(l -> four().map(d -> d.move(l))).filter(l -> !places.a().contains(l) || places.a().getChar(l) != '#').forEach(set::add);
    System.out.println(set.size());
    i++;
    return new Pair<>(places.a(), set);
  }

  public Pair<InfiniteGrid, Set<Loc>> doTurn2(Pair<InfiniteGrid, Set<Loc>> places) {
    var set = new HashSet<Loc>();
    places.b().stream().flatMap(l -> four().map(d -> d.move(l))).filter(l -> places.a().getChar(l) != '#').forEach(set::add);
    if (places.b().stream().anyMatch(l -> !places.a().contains(l))) {
      System.out.println("DIE");
      throw new IllegalStateException();
    }
    System.out.println(set.size());
    i++;
    return new Pair<>(places.a(), set);
  }
}
