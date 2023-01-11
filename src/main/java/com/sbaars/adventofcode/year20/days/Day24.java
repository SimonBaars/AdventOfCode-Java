package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.common.HexDirection;
import com.sbaars.adventofcode.year20.Day2020;

import java.awt.*;
import java.util.List;
import java.util.*;

import static java.util.Arrays.stream;

public class Day24 extends Day2020 implements HasRecursion {


  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  @Override
  public Object part1() {
    Set<Point> visited = getVisited();
    return visited.size();
  }

  private Set<Point> getVisited() {
    Set<Point> visited = new HashSet<>();
    var input = stream(dayStrings()).map(this::read).toList();
    for (List<HexDirection> dirs : input) {
      Point pos = new Point(0, 0);
      for (HexDirection dir : dirs) {
        pos = dir.move(pos);
      }
      if (!visited.add(pos)) {
        visited.remove(pos);
      }
    }
    return visited;
  }

  public List<HexDirection> read(String dirs) {
    List<HexDirection> res = new ArrayList<>(dirs.length());
    while (dirs.length() > 0) {
      Optional<HexDirection> direction;
      if (dirs.length() > 1 && (direction = HexDirection.get(dirs.substring(0, 2))).isPresent()) {
        res.add(direction.get());
        dirs = dirs.substring(2);
      } else if ((direction = HexDirection.get(dirs.substring(0, 1))).isPresent()) {
        res.add(direction.get());
        dirs = dirs.substring(1);
      }
    }
    return res;
  }

  @Override
  public Object part2() {
    Builder<Set<Point>> visited = new Builder<>(getVisited(), HashSet::new);
    for (int i = 0; i < 100; i++) {
      visited.get().forEach(p -> addNeighbors(visited.get(), visited.getNew(), new HashSet<>(), p, true));
      visited.refresh();
    }
    return visited.get().size();
  }

  public void addNeighbors(Set<Point> pos, Set<Point> newPos, Set<Point> checkedPos, Point p, boolean active) {
    if (!checkedPos.contains(p)) {
      int neighbours = 0;
      checkedPos.add(p);
      for (HexDirection dir : HexDirection.values()) {
        Point x = dir.move(p);
        if (pos.contains(x)) {
          neighbours++;
        } else if (active) {
          addNeighbors(pos, newPos, checkedPos, x, false);
        }
      }
      if ((active && (neighbours == 1 || neighbours == 2)) ||
          (!active && neighbours == 2)) {
        newPos.add(p);
      }
    }
  }
}
