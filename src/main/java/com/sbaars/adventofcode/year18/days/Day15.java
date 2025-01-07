package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import com.sbaars.adventofcode.common.location.Loc;
import java.util.*;
import java.util.stream.Collectors;

public class Day15 extends Day2018 {
  private static final int ATTACK_POWER = 3;
  private static final int INITIAL_HP = 200;

  private static class Unit {
    Loc position;
    int hp;
    char type; // 'E' for Elf, 'G' for Goblin
    boolean alive;

    Unit(Loc position, char type) {
      this.position = position;
      this.hp = INITIAL_HP;
      this.type = type;
      this.alive = true;
    }

    boolean isEnemy(Unit other) {
      return this.type != other.type;
    }
  }

  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  private List<Unit> parseUnits(char[][] grid) {
    List<Unit> units = new ArrayList<>();
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[y].length; x++) {
        if (grid[y][x] == 'E' || grid[y][x] == 'G') {
          units.add(new Unit(new Loc(x, y), grid[y][x]));
        }
      }
    }
    return units;
  }

  private List<Loc> getAdjacentSpaces(Loc pos) {
    List<Loc> adjacent = new ArrayList<>();
    // Order: up, left, right, down (reading order for equal distances)
    adjacent.add(new Loc(pos.x, pos.y - 1));
    adjacent.add(new Loc(pos.x - 1, pos.y));
    adjacent.add(new Loc(pos.x + 1, pos.y));
    adjacent.add(new Loc(pos.x, pos.y + 1));
    return adjacent;
  }

  private Optional<Loc> findNextMove(Unit unit, List<Unit> enemies, char[][] grid) {
    if (enemies.isEmpty()) return Optional.empty();

    // Check if already in range of an enemy
    boolean inRange = false;
    for (Loc adj : getAdjacentSpaces(unit.position)) {
      if (isEnemy(adj, unit.type, grid)) {
        inRange = true;
        break;
      }
    }
    if (inRange) return Optional.empty();

    // Find all target squares (empty squares adjacent to enemies)
    Set<Loc> targets = new HashSet<>();
    for (Unit enemy : enemies) {
      for (Loc adj : getAdjacentSpaces(enemy.position)) {
        if (grid[adj.intY()][adj.intX()] == '.') {
          targets.add(adj);
        }
      }
    }
    if (targets.isEmpty()) return Optional.empty();

    // Find reachable targets using BFS
    Map<Loc, Integer> distances = new HashMap<>();
    Map<Loc, Loc> firstStep = new HashMap<>();
    Queue<Loc> queue = new LinkedList<>();
    
    // Add initial moves
    for (Loc adj : getAdjacentSpaces(unit.position)) {
      if (grid[adj.intY()][adj.intX()] == '.') {
        distances.put(adj, 1);
        firstStep.put(adj, adj);
        queue.add(adj);
      }
    }

    // BFS
    while (!queue.isEmpty()) {
      Loc current = queue.poll();
      int dist = distances.get(current);

      for (Loc next : getAdjacentSpaces(current)) {
        if (grid[next.intY()][next.intX()] == '.' && !distances.containsKey(next)) {
          distances.put(next, dist + 1);
          firstStep.put(next, firstStep.get(current));
          queue.add(next);
        }
      }
    }

    // Find nearest reachable target
    Loc chosen = null;
    int minDist = Integer.MAX_VALUE;

    for (Loc target : targets) {
      if (distances.containsKey(target)) {
        int dist = distances.get(target);
        if (dist < minDist || 
            (dist == minDist && (target.y < chosen.y || 
             (target.y == chosen.y && target.x < chosen.x)))) {
          minDist = dist;
          chosen = target;
        }
      }
    }

    return chosen == null ? Optional.empty() : Optional.of(firstStep.get(chosen));
  }

  private boolean isEnemy(Loc pos, char myType, char[][] grid) {
    char c = grid[pos.intY()][pos.intX()];
    return (myType == 'E' && c == 'G') || (myType == 'G' && c == 'E');
  }

  private Optional<Unit> findTarget(Unit unit, List<Unit> units, char[][] grid) {
    Unit target = null;
    int minHp = Integer.MAX_VALUE;

    for (Loc adj : getAdjacentSpaces(unit.position)) {
      if (isEnemy(adj, unit.type, grid)) {
        for (Unit enemy : units) {
          if (enemy.alive && enemy.position.equals(adj)) {
            if (enemy.hp < minHp || 
                (enemy.hp == minHp && (enemy.position.y < target.position.y || 
                 (enemy.position.y == target.position.y && enemy.position.x < target.position.x)))) {
              minHp = enemy.hp;
              target = enemy;
            }
          }
        }
      }
    }

    return Optional.ofNullable(target);
  }

  @Override
  public Object part1() {
    char[][] grid = dayGrid();
    List<Unit> units = parseUnits(grid);
    int rounds = 0;
    
    while (true) {
      // Sort units in reading order
      units.sort((a, b) -> {
        if (a.position.y != b.position.y) return Long.compare(a.position.y, b.position.y);
        return Long.compare(a.position.x, b.position.x);
      });

      boolean roundCompleted = true;
      
      // Take a copy of the units list to handle deaths
      for (Unit unit : new ArrayList<>(units)) {
        if (!unit.alive) continue;

        // Check if combat has ended
        List<Unit> enemies = units.stream()
            .filter(u -> u.alive && unit.isEnemy(u))
            .collect(Collectors.toList());

        if (enemies.isEmpty()) {
          roundCompleted = false;
          break;
        }

        // Move phase
        findNextMove(unit, enemies, grid).ifPresent(newPos -> {
          grid[unit.position.intY()][unit.position.intX()] = '.';
          unit.position = newPos;
          grid[newPos.intY()][newPos.intX()] = unit.type;
        });

        // Attack phase
        findTarget(unit, units, grid).ifPresent(target -> {
          target.hp -= ATTACK_POWER;
          if (target.hp <= 0) {
            target.alive = false;
            grid[target.position.intY()][target.position.intX()] = '.';
          }
        });
      }

      if (!roundCompleted) {
        int totalHp = units.stream()
            .filter(u -> u.alive)
            .mapToInt(u -> u.hp)
            .sum();
        return rounds * totalHp;
      }

      rounds++;
    }
  }

  @Override
  public Object part2() {
    return "";
  }
}
