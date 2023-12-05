package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.Tuple;
import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.pow;
import static java.util.Arrays.stream;

public class Day4 extends Day2023 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  public record Card(int number, List<Integer> winning, List<Integer> nums) {}

  @Override
  public Object part1() {
    return countNumberWon()
            .mapToLong(nWon -> (long) pow(2, nWon - 1))
            .sum();
  }

  private Stream<Long> countNumberWon() {
    return stream(day().replace("  ", " ").split("\n"))
            .map(s -> readString(s, "Card %i: %li | %li", " ", Card.class, Integer.class))
            .map(c -> c.winning.stream().filter(c.nums::contains).count());
  }

  @Override
  public Object part2() {
    List<Long> numberWon = countNumberWon().toList();
    var lcm = new LongCountMap<Integer>();
    for(int i = 0; i<numberWon.size(); i++) {
      long number = lcm.get(i) + 1;
      for(int k = 0; k<numberWon.get(i); k++) {
        lcm.increment(i+k+1, number);
      }
    }
    return lcm.sum() + numberWon.size();
  }
}
