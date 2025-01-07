package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.*;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day22 extends Day2023 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  static int idCounter = 0;

  public record Brick(int id, List<Loc3D> cubes) {
    public Brick(long x1, long y1, long z1, long x2, long y2, long z2) {
      this(idCounter++, new Loc3D(x1, y1, z1).lineTo(new Loc3D(x2, y2, z2)));
    }
  }

  @Override
  public Object part1() {
    return solve(true);
  }

  @Override
  public Object part2() {
    return solve(false);
  }

  private long solve(boolean part1) {
    var bricks = dayStream()
        .map(s -> readString(s, "%n,%n,%n~%n,%n,%n", Brick.class))
        .sorted(Comparator.comparingLong(b -> b.cubes.get(0).z))
        .toList();
    
    var settledBricks = dropBricks(bricks);
    var supportedBy = new HashMap<Brick, Set<Brick>>();
    var supports = new HashMap<Brick, Set<Brick>>();
    
    settledBricks.forEach(b -> {
        supportedBy.put(b, new HashSet<>());
        supports.put(b, new HashSet<>());
    });
    
    settledBricks.forEach(upper -> 
        settledBricks.stream()
            .filter(lower -> !lower.equals(upper) && isSupporting(lower, upper))
            .forEach(lower -> {
                supportedBy.get(upper).add(lower);
                supports.get(lower).add(upper);
            }));

    if (part1) {
        return settledBricks.stream()
            .filter(b -> supports.get(b).stream()
                .allMatch(supported -> supportedBy.get(supported).size() > 1))
            .count();
    }

    return settledBricks.stream()
        .mapToLong(brick -> {
            Set<Brick> falling = new HashSet<>();
            falling.add(brick);
            Queue<Brick> queue = new ArrayDeque<>(supports.get(brick));
            
            while (!queue.isEmpty()) {
                Brick current = queue.poll();
                if (!falling.contains(current) && 
                    supportedBy.get(current).stream().allMatch(falling::contains)) {
                    falling.add(current);
                    queue.addAll(supports.get(current));
                }
            }
            return falling.size() - 1;
        }).sum();
  }

  private boolean isSupporting(Brick lower, Brick upper) {
    return lower.cubes().stream().anyMatch(c -> 
        upper.cubes.stream().anyMatch(uc -> 
            uc.x == c.x && uc.y == c.y && uc.z == c.z + 1));
  }

  private Set<Brick> dropBricks(List<Brick> bricks) {
    Set<Brick> result = new HashSet<>();
    Map<Loc3D, Brick> occupiedSpaces = new HashMap<>();
    
    for (Brick brick : bricks) {
        List<Loc3D> newPos = new ArrayList<>(brick.cubes);
        long maxZ = brick.cubes.stream()
            .mapToLong(cube -> {
                for (long z = cube.z - 1; z >= 1; z--) {
                    if (occupiedSpaces.containsKey(new Loc3D(cube.x, cube.y, z))) {
                        return z + 1;
                    }
                }
                return 1L;
            })
            .max()
            .orElse(1L);
        
        long drop = brick.cubes.get(0).z - maxZ;
        for (int i = 0; i < newPos.size(); i++) {
            Loc3D cube = newPos.get(i);
            newPos.set(i, new Loc3D(cube.x, cube.y, cube.z - drop));
        }
        
        Brick droppedBrick = new Brick(brick.id, newPos);
        result.add(droppedBrick);
        droppedBrick.cubes.forEach(cube -> occupiedSpaces.put(cube, droppedBrick));
    }
    
    return result;
  }
}
