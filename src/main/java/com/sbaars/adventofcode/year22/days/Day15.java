package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.List;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;

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
  record Pos(Loc sensor, Loc beacon) {}

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
    long covered = 0;
    for(int i = 0; i<=4000000; i++) {
      if(i % 10000 == 0) System.out.println(i);
      outer: for(int j = 0; j<=4000000; j++) {
        long num = 0;
        for (Pos p : posList) {
          Loc l = new Loc(i, j);
          long distance = p.sensor.distance(p.beacon);
          long distance2 = l.distance(p.sensor);
          if (distance2 > distance && g.get(l).isEmpty()) {
            num++;
            //          continue outer;
          } else {
            continue outer;
          }
        }
        if(num == posList.size()) {
          System.out.println(((i*4000000)+j));
        }
      }
    }
    return covered;
  }
}
