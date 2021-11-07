package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.HashSet;
import java.util.Set;

public class Day17 extends Day2020 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  @Override
  public Object part1() {
    Set<Pos> pos = new HashSet<>();
    char[][] input = dayGrid();
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        if (input[i][j] == '#') {
          pos.add(new Pos(i, j, 0));
        }
      }
    }
    for (int i = 0; i < 6; i++) {
      Set<Pos> newPos = new HashSet<>();
      Set<Pos> checkedPos = new HashSet<>();
      for (Pos p : pos) {
        addNeighbors(pos, newPos, checkedPos, p, true);
      }
      pos = newPos;
    }
    return pos.size();
  }

  public void addNeighbors(Set<Pos> pos, Set<Pos> newPos, Set<Pos> checkedPos, Pos p, boolean active) {
    if (!checkedPos.contains(p)) {
      long neighbours = active ? -1 : 0;
      checkedPos.add(p);
      for (int a = -1; a <= 1; a++) {
        for (int b = -1; b <= 1; b++) {
          for (int c = -1; c <= 1; c++) {
            Pos x = new Pos(p.x + a, p.y + b, p.z + c);
            if (pos.contains(x)) {
              neighbours++;
            } else if (active) {
              addNeighbors(pos, newPos, checkedPos, x, false);
            }
          }
        }
      }
      if ((active && (neighbours == 2 || neighbours == 3)) ||
          (!active && neighbours == 3)) {
        newPos.add(p);
      }
    }
  }

  @Override
  public Object part2() {
    Set<Pos4> pos = new HashSet<>();
    char[][] input = dayGrid();
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        if (input[i][j] == '#') {
          pos.add(new Pos4(i, j, 0, 0));
        }
      }
    }
    for (int i = 0; i < 6; i++) {
      Set<Pos4> newPos = new HashSet<>();
      Set<Pos4> checkedPos = new HashSet<>();
      for (Pos4 p : pos) {
        addNeighbors(pos, newPos, checkedPos, p, true);
      }
      pos = newPos;
    }
    return pos.size();
  }

  public void addNeighbors(Set<Pos4> pos, Set<Pos4> newPos, Set<Pos4> checkedPos, Pos4 p, boolean active) {
    if (!checkedPos.contains(p)) {
      long neighbours = active ? -1 : 0;
      checkedPos.add(p);
      for (int a = -1; a <= 1; a++) {
        for (int b = -1; b <= 1; b++) {
          for (int c = -1; c <= 1; c++) {
            for (int d = -1; d <= 1; d++) {
              Pos4 x = new Pos4(p.x + a, p.y + b, p.z + c, p.w + d);
              if (pos.contains(x)) {
                neighbours++;
              } else if (active) {
                addNeighbors(pos, newPos, checkedPos, x, false);
              }
            }
          }
        }
      }
      if ((active && (neighbours == 2 || neighbours == 3)) ||
          (!active && neighbours == 3)) {
        newPos.add(p);
      }
    }
  }

  public static record Pos(long x, long y, long z) {
  }

  public static record Pos4(long x, long y, long z, long w) {
  }
}
