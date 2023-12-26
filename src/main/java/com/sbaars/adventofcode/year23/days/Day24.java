package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Day24 extends Day2023 {

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  public record Rock(Loc3D p, Loc3D v) {
    private static final long min = 200000000000000L;
    private static final long max = 400000000000000L;

    public Rock(long posX, long posY, long posZ, long velX, long velY, long velZ) {
      this(new Loc3D(posX, posY, posZ), new Loc3D(velX, velY, velZ));
    }

    public Rock positionAt(long t) {
      return new Rock(p.move(v.multiply(t)), v);
    }

    boolean willReach(double x, double y) {
      double dx = x - p.x;
      double dy = y - p.y;
      return switch (dx > 0 ? 1 : dx < 0 ? -1 : 0) {
        case 0 -> switch (dy > 0 ? 1 : dy < 0 ? -1 : 0) {
          case 0 -> false;
          case 1 -> v.y > 0;
          default -> v.y < 0;
        };
        case 1 -> v.x > 0;
        default -> v.x < 0;
      };
    }

    boolean intersectsXY(Rock o) {
      double d = -v.y * o.v.x + o.v.y * v.x;
      if (d != 0) {
        double a = v.y * p.x - v.x * p.y;
        double b = o.v.y * o.p.x - o.v.x * o.p.y;
        double x = (-o.v.x * a + v.x * b) / d;
        double y = (-o.v.y * a + v.y * b) / d;
        if (willReach(x, y) && o.willReach(x, y)) {
          return x >= min && x <= max && y >= min && y <= max;
        }
      }
      return false;
    }

    public Loc x() {
      return new Loc(p.x, v.x);
    }

    public Loc y() {
      return new Loc(p.y, v.y);
    }

    public Loc z() {
      return new Loc(p.z, v.z);
    }

  }

  @Override
  public Object part1() {
    var in = dayStream().map(s -> readString(s, "%n, %n, %n @ %n, %n, %n", Rock.class)).toList();
    return range(0, in.size())
        .mapToLong(i -> range(i + 1, in.size())
            .filter(j -> in.get(i).intersectsXY(in.get(j)))
            .count())
        .sum();
  }

  @Override
  public Object part2() {
    var in = dayStream().map(s -> readString(s, "%n, %n, %n @ %n, %n, %n", Rock.class)).toList();
    List<Function<Rock, Loc>> slicers = List.of(Rock::x, Rock::y, Rock::z);
    var collisionRecords = slicers.stream().flatMap(slicer -> in.stream().filter(
        candidate -> in.stream().filter(h -> !h.equals(candidate)).allMatch(h -> {
              var differenceInPosition = slicer.apply(h).x - slicer.apply(candidate).x;
              var differenceInVelocity = slicer.apply(h).y - slicer.apply(candidate).y;
              return differenceInVelocity != 0 && differenceInPosition % differenceInVelocity == 0 && differenceInPosition / differenceInVelocity < 0;
            }
        )).map(candidate ->
        in.stream().filter(h -> !h.equals(candidate)).map(h -> {
          var collisionTime = (slicer.apply(h).x - slicer.apply(candidate).x) / (slicer.apply(candidate).y - slicer.apply(h).y);
          return new Pair<>(collisionTime, h.positionAt(collisionTime));
        }).collect(toMap(Pair::a, Pair::b, (a, b) -> a, TreeMap::new)))
    ).toList();
    var result = collisionRecords.get(0).entrySet().stream().limit(2).toList();
    var velocity = result.get(1).getValue().p().move(result.get(0).getValue().p().multiply(-1)).divide(result.get(1).getKey() - result.get(0).getKey());
    var magic = result.get(0).getValue().p().move(velocity.multiply(-result.get(0).getKey()));
    return magic.x + magic.y + magic.z;
  }
}
