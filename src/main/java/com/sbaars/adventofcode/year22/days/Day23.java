package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
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
    InfiniteGrid grid = new InfiniteGrid(dayGrid(), '.');
    List<Direction> dirs = new ArrayList<>(List.of(NORTH, SOUTH, WEST, EAST));
    Set<Loc> elves = new HashSet<>(grid.grid.keySet());
    
    for (int i = 0; isPart2 || i < 10; i++) {
      Map<Loc, Loc> proposals = new HashMap<>();
      Map<Loc, Integer> proposalCounts = new HashMap<>();
      
      // First half: propose moves
      for (Loc elf : elves) {
        boolean hasNeighbor = false;
        List<Direction> eightDirs = Direction.eight().collect(Collectors.toList());
        for (Direction d : eightDirs) {
          if (elves.contains(d.move(elf))) {
            hasNeighbor = true;
            break;
          }
        }
        
        if (hasNeighbor) {
          for (Direction d : dirs) {
            Loc moved = d.move(elf);
            if (!elves.contains(moved) && 
                !elves.contains(d.turn().move(moved)) && 
                !elves.contains(d.turn(false).move(moved))) {
              proposals.put(elf, moved);
              proposalCounts.merge(moved, 1, Integer::sum);
              break;
            }
          }
        }
      }
      
      // Second half: move elves
      boolean anyMoved = false;
      Set<Loc> newElves = new HashSet<>();
      
      for (Loc elf : elves) {
        Loc proposed = proposals.get(elf);
        if (proposed != null && proposalCounts.get(proposed) == 1) {
          newElves.add(proposed);
          anyMoved = true;
        } else {
          newElves.add(elf);
        }
      }
      
      if (!anyMoved) {
        return i + 1;
      }
      
      elves = newElves;
      dirs.add(dirs.remove(0));
    }
    
    // Calculate empty ground tiles
    long minX = Long.MAX_VALUE, maxX = Long.MIN_VALUE;
    long minY = Long.MAX_VALUE, maxY = Long.MIN_VALUE;
    
    for (Loc elf : elves) {
      minX = Math.min(minX, elf.x);
      maxX = Math.max(maxX, elf.x);
      minY = Math.min(minY, elf.y);
      maxY = Math.max(maxY, elf.y);
    }
    
    return (maxX - minX + 1) * (maxY - minY + 1) - elves.size();
  }
}
