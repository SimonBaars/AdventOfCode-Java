package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.Arrays;
import java.util.List;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.common.grid.InfiniteGrid.toInfiniteGrid;
import static java.lang.Math.toIntExact;

public class Day15 extends Day2022 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
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
    Range target = new Range(new Loc(0, 0), new Loc(4000000, 4000000));
    return posList.stream().flatMap(p ->
                    Arrays.stream(Direction.fourDirections()).flatMap(d -> d.move(p.sensor, toIntExact(p.distance()) + 1).walk(d.turnSteps(3), toIntExact(p.distance() + 2)))
            ).filter(target::inRange).filter(l -> posList.stream().allMatch(p -> l.distance(p.sensor) > p.distance())).findAny().get();
  }
}
