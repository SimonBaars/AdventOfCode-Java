package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.List;

import static com.sbaars.adventofcode.util.AOCUtils.connectedPairs;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day9 extends Day2023 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  @Override
  public Object part1() {
    return solution(true);
  }

  private long solution(boolean part1) {
    List<List<Long>> in = dayStream().map(s -> (List<Long>)readString(s, "%ln", " ", ArrayList.class)).toList();
    return in.stream().mapToLong(longs -> (part1 ? longs.get(longs.size() - 1) + calcRes(longs, true) : longs.get(0) - calcRes(longs, false))).sum();
  }

  private long calcRes(List<Long> longs, boolean part1) {
    List<Long> deltas = connectedPairs(longs).map(p -> difference(p.a(), p.b())).toList();
    if(deltas.stream().distinct().count() > 1L) {
      return part1 ? deltas.get(deltas.size()-1) + calcRes(deltas, true) : deltas.get(0) - calcRes(deltas, false);
    }
    return deltas.get(deltas.size()-1);
  }

  public long difference(long l1, long l2) {
    return l2 - l1;
  }


  @Override
  public Object part2() {
    return solution(false);
  }
}
