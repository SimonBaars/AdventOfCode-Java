package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year21.Day2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.math.BigInteger.ZERO;

public class Day22 extends Day2021 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  @Override
  public Object part1() {
    var in = dayStream().map(e -> readString(e, "%s x=%n..%n,y=%n..%n,z=%n..%n", Cube.class)).toList();
    Set<Loc3D> locs = new HashSet<>();
    for (Cube c : in) {
      for (long i = Math.max(c.xStart, -50); i <= Math.min(c.xEnd, 50); i++) {
        for (long j = Math.max(c.yStart, -50); j <= Math.min(c.yEnd, 50); j++) {
          for (long k = Math.max(c.zStart, -50); k <= Math.min(c.zEnd, 50); k++) {
            Loc3D l = new Loc3D(i, j, k);
            if (c.status.equals("on")) {
              locs.add(l);
            } else {
              locs.remove(l);
            }
          }
        }
      }
    }
    return locs.size();
  }

  @Override
  public Object part2() {
    var in = dayStream().map(e -> readString(e, "%s x=%n..%n,y=%n..%n,z=%n..%n", Cube.class)).toList();
    List<Cube> cubes = new ArrayList<>();
    for (Cube cur : in) {
      Cube cu = new Cube(cur.status, cur.xStart, cur.xEnd + 1, cur.yStart, cur.yEnd + 1, cur.zStart, cur.zEnd + 1);
      cubes = cubes.stream().flatMap(c -> c.getSubCubes(cu)).collect(Collectors.toCollection(ArrayList::new));
      if (cu.status.equals("on")) {
        cubes.add(cu);
      }
    }
    return cubes.stream().map(this::cubeSize).reduce(BigInteger::add).orElse(ZERO);
  }

  private BigInteger cubeSize(Cube c) {
    return BigInteger.valueOf(c.xEnd - c.xStart).multiply(BigInteger.valueOf(c.yEnd - c.yStart)).multiply(BigInteger.valueOf(c.zEnd - c.zStart));
  }

  public record Cube(String status, long xStart, long xEnd, long yStart, long yEnd, long zStart, long zEnd) {
    private Stream<Cube> getSubCubes(Cube c2) {
      if (c2.contains(this)) {
        return Stream.of();
      } else if (!touches(c2)) {
        return Stream.of(this);
      }

      var xIntersect = Stream.of(c2.xStart, c2.xEnd).filter(x -> xStart < x && x < xEnd).collect(Collectors.toCollection(ArrayList::new));
      var yIntersect = Stream.of(c2.yStart, c2.yEnd).filter(y -> yStart < y && y < yEnd).collect(Collectors.toCollection(ArrayList::new));
      var zIntersect = Stream.of(c2.zStart, c2.zEnd).filter(z -> zStart < z && z < zEnd).collect(Collectors.toCollection(ArrayList::new));

      xIntersect.add(0, xStart);
      xIntersect.add(xEnd);
      yIntersect.add(0, yStart);
      yIntersect.add(yEnd);
      zIntersect.add(0, zStart);
      zIntersect.add(zEnd);

      var res = new ArrayList<Cube>();
      for (int i = 0; i < xIntersect.size() - 1; i++) {
        for (int j = 0; j < yIntersect.size() - 1; j++) {
          for (int k = 0; k < zIntersect.size() - 1; k++) {
            res.add(new Cube(status, xIntersect.get(i), xIntersect.get(i + 1), yIntersect.get(j), yIntersect.get(j + 1), zIntersect.get(k), zIntersect.get(k + 1)));
          }
        }
      }
      return res.stream().filter(c -> !c2.contains(c));
    }

    private boolean contains(Cube b) {
      return xStart <= b.xStart && xEnd >= b.xEnd && yStart <= b.yStart && yEnd >= b.yEnd && zStart <= b.zStart && zEnd >= b.zEnd;
    }

    private boolean touches(Cube b) {
      return xStart <= b.xEnd && xEnd >= b.xStart && yStart <= b.yEnd && yEnd >= b.yStart && zStart <= b.zEnd && zEnd >= b.zStart;
    }
  }
}
