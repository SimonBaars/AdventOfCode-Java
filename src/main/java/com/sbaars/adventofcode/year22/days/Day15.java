package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.DoubleLoc;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.util.AOCUtils;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.List;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;
import static java.lang.Math.*;

public class Day15 extends Day2022 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    Day d = new Day15();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Input(long sensorX, long sensorY, long beaconX, long beaconY) {}
  record Pos(Loc sensor, Loc beacon) {
    private long distance() {
      return sensor.distance(beacon);
    }
  }

  @Override
  public Object part1() {
    List<Pos> posList = dayStream().map(s -> readString(s, "Sensor at x=%n, y=%n: closest beacon is at x=%n, y=%n", Input.class)).map(i -> new Pos(new Loc(i.sensorX, i.sensorY), new Loc(i.beaconX, i.beaconY))).toList();
    InfiniteGrid g = posList.stream().map(Pos::sensor).collect(toInfiniteGrid('S'));
    posList.forEach(p -> g.set(p.beacon, 'B'));
    long covered = 0;
    outer: for(int i = -4000000; i<20000000; i++) {
      for(Pos p : posList) {
        Loc l =new Loc(i, 2000000);
        long distance = p.sensor.distance(p.beacon);
        long distance2 = l.distance(p.sensor);
        if(distance2 <= distance && g.get(l).isEmpty()){
//          System.out.println(l);
          covered++;
          continue outer;
        }
      }
    }
    return covered;
  }

  @Override
  public Object part2() {
    List<Pos> posList = dayStream().map(s -> readString(s, "Sensor at x=%n, y=%n: closest beacon is at x=%n, y=%n", Input.class)).map(i -> new Pos(new Loc(i.sensorX, i.sensorY), new Loc(i.beaconX, i.beaconY))).toList();
    InfiniteGrid g = posList.stream().map(Pos::sensor).collect(toInfiniteGrid('S'));
    posList.forEach(p -> g.set(p.beacon, 'B'));
    Range target = new Range(new Loc(0, 0), new Loc(4000000, 4000000));
    Loc cor = new Loc(3068581, 3017867);
    System.out.println(posList.stream().allMatch(p -> cor.distance(p.sensor) > p.distance()));

    return AOCUtils.allPairs(posList).flatMap(p -> {
      Pos p1 = p.a();
      Pos p2 = p.b();
      List<Loc> inter = circleCircleIntersectionPoints(p1.sensor, p2.sensor, p1.distance(), p2.distance());
      int x0 = p1.sensor.intX();
      int x1 = p2.sensor.intX();
      int y0 = p1.sensor.intY();
      int y1 = p2.sensor.intY();
      long r0 = p1.sensor.distance(p1.beacon);
      long r1 = p2.sensor.distance(p2.beacon);
      double d= sqrt((x1-x0)^2 + (y1-y0)^2);
      double a=(pow(r0,2)-pow(r1,2)+pow(d, 2))/(2*d);
      double h=sqrt(pow(r0,2)-pow(a,2));
      double x2=x0+a*(x1-x0)/d;
      double y2=y0+a*(y1-y0)/d;
      double x3=x2+h*(y1-y0)/d;
      double y3=y2-h*(x1-x0)/d;
//      return Stream.concat(new Loc((long)x2, (long)y2).eightDirs(), new Loc((long)x3, (long)y3).eightDirs());
      return inter.stream().filter(l -> target.inRange(l)).map(l -> l.expand(1000000));
    }).peek(System.out::println).filter(l -> target.inRange(l)).filter(l -> posList.stream().allMatch(p -> l.distance(p.sensor) > p.distance())).findAny().get();
//    for(int i = 0; i<4000000; i++) {
//      List<Range> safeRange = new ArrayList<>();
//      for (Pos p : posList) {
//        long distance = p.sensor.distance(p.beacon);
//
//      }
//    }
//    long covered = 0;
//    for(int i = 0; i<=4000000; i++) {
//      if(i % 10000 == 0) System.out.println(i);
//      outer: for(int j = 0; j<=4000000; j++) {
//        long num = 0;
//        for (Pos p : posList) {
//          Loc l = new Loc(i, j);
//          long distance = p.sensor.distance(p.beacon);
//          long distance2 = l.distance(p.sensor);
//          if (distance2 > distance && g.get(l).isEmpty()) {
//            num++;
//            //          continue outer;
//          } else {
//            continue outer;
//          }
//        }
//        if(num == posList.size()) {
//          System.out.println(((i*4000000)+j));
//        }
//      }
//    }
//    return covered;
  }

  private List<Loc> circleCircleIntersectionPoints(Loc c1, Loc c2, long r1, long r2) {
    double EPS = 0.0000001;

    double r, R, d, dx, dy, cx, cy, Cx, Cy;

    if (r1 < r2) {
      r  = r1;  R = r2;
      cx = c1.x; cy = c1.y;
      Cx = c2.x; Cy = c2.y;
    } else {
      r  = r2; R  = r1;
      Cx = c1.x; Cy = c1.y;
      cx = c2.x; cy = c2.y;
    }

    // Compute the vector <dx, dy>
    dx = cx - Cx;
    dy = cy - Cy;

    // Find the distance between two points.
    d = Math.sqrt( dx*dx + dy*dy );

    // There are an infinite number of solutions
    // Seems appropriate to also return null
    if (d < EPS && Math.abs(R-r) < EPS) return List.of();

    // No intersection (circles centered at the
    // same place with different size)
  else if (d < EPS) return List.of();

    var x = (dx / d) * R + Cx;
    var y = (dy / d) * R + Cy;
    var P = new DoubleLoc(x, y);

    // Single intersection (kissing circles)
    if (Math.abs((R+r)-d) < EPS || Math.abs(R-(r+d)) < EPS) return List.of(new Loc(round(x), round(y)));

    // No intersection. Either the small circle contained within
    // big circle or circles are simply disjoint.
    if ( (d+r) < R || (R+r < d) ) return List.of();

    var C = new DoubleLoc(Cx, Cy);
    var angle = acossafe((r*r-d*d-R*R)/(-2.0*d*R));
    var pt1 = rotatePoint(C, P, +angle);
    var pt2 = rotatePoint(C, P, -angle);
    return List.of(pt1, pt2);

  }

  double acossafe(double x) {
    if (x >= +1.0) return 0;
    if (x <= -1.0) return Math.PI;
    return Math.acos(x);
  }

  Loc rotatePoint(DoubleLoc fp, DoubleLoc pt, double a) {
    var x = pt.x - fp.x;
    var y = pt.y - fp.y;
    var xRot = x * Math.cos(a) + y * Math.sin(a);
    var yRot = y * Math.cos(a) - x * Math.sin(a);
    return new Loc(round(fp.x+xRot),round(fp.y+yRot));
  }

}
