package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year18.Day2018;

import static com.sbaars.adventofcode.util.AOCUtils.*;
import static java.lang.Math.abs;
import static java.util.stream.IntStream.range;

public class Day5 extends Day2018 {

  private static int DIFF = 'a' - 'A';

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  @Override
  public Object part1() {
    return fixedPoint(day().trim(), this::react).length();
  }

  public String react(String s) {
    StringBuilder b = new StringBuilder(s);
    int[] removeIndices = zip(range(0, s.length()).boxed(), connectedPairs(s.chars().boxed().toList()), (i, p) -> new Pair<>(i, abs(p.a() - p.b()) - DIFF == 0)).filter(Pair::b).mapToInt(Pair::a).toArray();
    for(int i = removeIndices.length-1; i>=0; i--){
      int index = removeIndices[i];
      if(i == 0 || removeIndices[i-1] != index-1) {
        b.replace(index, index+2, "");
      }
    }
    return b.toString();
  }

  @Override
  public Object part2() {
    return "";
  }
}
