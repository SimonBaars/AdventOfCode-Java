package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;

public class Day6 extends Day2020 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    return dayStream("\n\n")
            .map(s -> s.replace("\n", ""))
            .mapToLong(s -> s.chars().distinct().count())
            .sum();
  }

  @Override
  public Object part2() {
    return stream(day().split("\n\n")).mapToInt(group -> {
      String[] people = group.split("\n");
      List<Integer> c = people[0].chars().boxed().collect(toCollection(ArrayList::new));
      range(1, people.length).forEach(i -> c.retainAll(people[i].chars().boxed().toList()));
      return c.size();
    }).sum();
  }
}
