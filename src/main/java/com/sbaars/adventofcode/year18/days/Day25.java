package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;

public class Day25 extends Day2018 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  record Point4D(int x, int y, int z, int w) {
    public int manhattanDistance(Point4D other) {
      return Math.abs(x - other.x) + Math.abs(y - other.y) + 
             Math.abs(z - other.z) + Math.abs(w - other.w);
    }
  }

  private List<Point4D> parseInput() {
    return dayStream()
        .map(line -> {
          String[] parts = line.split(",");
          return new Point4D(
              Integer.parseInt(parts[0].trim()),
              Integer.parseInt(parts[1].trim()),
              Integer.parseInt(parts[2].trim()),
              Integer.parseInt(parts[3].trim())
          );
        })
        .toList();
  }

  private void addToConstellation(Point4D point, Set<Point4D> constellation, 
                                Set<Point4D> remaining) {
    constellation.add(point);
    remaining.remove(point);
    
    List<Point4D> connected = remaining.stream()
        .filter(p -> point.manhattanDistance(p) <= 3)
        .toList();
    
    for (Point4D p : connected) {
      addToConstellation(p, constellation, remaining);
    }
  }

  private List<Set<Point4D>> findConstellations(List<Point4D> points) {
    List<Set<Point4D>> constellations = new ArrayList<>();
    Set<Point4D> remaining = new HashSet<>(points);
    
    while (!remaining.isEmpty()) {
      Set<Point4D> constellation = new HashSet<>();
      addToConstellation(remaining.iterator().next(), constellation, remaining);
      constellations.add(constellation);
    }
    
    return constellations;
  }

  @Override
  public Object part1() {
    List<Point4D> points = parseInput();
    return findConstellations(points).size();
  }

  @Override
  public Object part2() {
    return "Merry Christmas!";
  }
}
