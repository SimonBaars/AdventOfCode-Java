package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day23 extends Day2018 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  record Nanobot(long x, long y, long z, long r) {
    public long distanceTo(Nanobot other) {
      return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }

    public long distanceToPoint(long px, long py, long pz) {
      return Math.abs(x - px) + Math.abs(y - py) + Math.abs(z - pz);
    }
  }

  record Point(long x, long y, long z) {
    public long distanceToOrigin() {
      return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
  }

  private List<Nanobot> parseInput() {
    Pattern pattern = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)");
    return dayStream().map(line -> {
      Matcher m = pattern.matcher(line);
      if (!m.find()) throw new IllegalStateException("Invalid input: " + line);
      return new Nanobot(
        Long.parseLong(m.group(1)),
        Long.parseLong(m.group(2)),
        Long.parseLong(m.group(3)),
        Long.parseLong(m.group(4))
      );
    }).toList();
  }

  @Override
  public Object part1() {
    List<Nanobot> nanobots = parseInput();
    Nanobot strongest = nanobots.stream()
        .max((a, b) -> Long.compare(a.r(), b.r()))
        .orElseThrow();
    
    return nanobots.stream()
        .filter(bot -> strongest.distanceTo(bot) <= strongest.r())
        .count();
  }

  private int countBotsInRange(List<Nanobot> bots, long x, long y, long z) {
    return (int) bots.stream()
        .filter(bot -> bot.distanceToPoint(x, y, z) <= bot.r())
        .count();
  }

  private Point findBestPoint(List<Nanobot> bots) {
    // Find bounds of the search space
    long minX = bots.stream().mapToLong(b -> b.x() - b.r()).min().getAsLong();
    long maxX = bots.stream().mapToLong(b -> b.x() + b.r()).max().getAsLong();
    long minY = bots.stream().mapToLong(b -> b.y() - b.r()).min().getAsLong();
    long maxY = bots.stream().mapToLong(b -> b.y() + b.r()).max().getAsLong();
    long minZ = bots.stream().mapToLong(b -> b.z() - b.r()).min().getAsLong();
    long maxZ = bots.stream().mapToLong(b -> b.z() + b.r()).max().getAsLong();

    Point bestPoint = null;
    int maxBotsInRange = 0;

    // Use decreasing step sizes to search the space
    for (long step = 1L << 30; step > 0; step /= 2) {
      for (long x = minX; x <= maxX; x += step) {
        for (long y = minY; y <= maxY; y += step) {
          for (long z = minZ; z <= maxZ; z += step) {
            int botsInRange = countBotsInRange(bots, x, y, z);
            Point currentPoint = new Point(x, y, z);

            if (botsInRange > maxBotsInRange || 
               (botsInRange == maxBotsInRange && 
                (bestPoint == null || currentPoint.distanceToOrigin() < bestPoint.distanceToOrigin()))) {
              maxBotsInRange = botsInRange;
              bestPoint = currentPoint;
            }
          }
        }
      }

      // Narrow the search space around the best point found
      if (bestPoint != null) {
        minX = Math.max(minX, bestPoint.x() - step);
        maxX = Math.min(maxX, bestPoint.x() + step);
        minY = Math.max(minY, bestPoint.y() - step);
        maxY = Math.min(maxY, bestPoint.y() + step);
        minZ = Math.max(minZ, bestPoint.z() - step);
        maxZ = Math.min(maxZ, bestPoint.z() + step);
      }
    }

    return bestPoint;
  }

  @Override
  public Object part2() {
    List<Nanobot> nanobots = parseInput();
    Point bestPoint = findBestPoint(nanobots);
    return bestPoint.distanceToOrigin();
  }
}
