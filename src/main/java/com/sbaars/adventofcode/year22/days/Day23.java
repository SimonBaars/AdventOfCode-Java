package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day23 extends Day2022 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  @Override
  public Object part1() {
    return solution(false);
  }

  @Override
  public Object part2() {
    return solution(true);
  }

  public long solution(boolean isPart2) {
    InfiniteGrid g = new InfiniteGrid(dayGrid());
    List<Direction> dirs = new ArrayList<>(List.of(NORTH, SOUTH, WEST, EAST));
    for(int i = 0; isPart2 || i<10; i++) {
      InfiniteGrid newGrid = new InfiniteGrid();
      InfiniteGrid finalG = g;
      Map<Loc, Loc> dest = g.grid.keySet().stream()
              .filter(e -> Arrays.stream(Direction.eightDirections()).anyMatch(d -> finalG.grid.containsKey(d.move(e))))
              .flatMap(e -> dirs.stream().filter(d -> !finalG.grid.containsKey(d.move(e)) && !finalG.grid.containsKey(d.turn().move(d.move(e)))  && !finalG.grid.containsKey(d.turn(false).move(d.move(e)))).map(d -> new Pair<>(e, d.move(e))).limit(1))
              .collect(Collectors.toMap(Pair::a, Pair::b));
      dest.entrySet().stream()
              .map(e -> dest.values().stream().filter(l -> l.equals(e.getValue())).limit(2).count() == 1 ? e.getValue() : e.getKey())
              .forEach(e -> newGrid.set(e, '#'));
      g.grid.keySet().stream()
              .filter(e -> !dest.containsKey(e))
              .forEach(e -> newGrid.set(e, '#'));
      dirs.add(dirs.remove(0));
      if(newGrid.grid.keySet().equals(g.grid.keySet())){
        return i+1;
      }
      g = newGrid;
    }
    return g.toString().chars().filter(c -> c == ' ').count();
  }
}
