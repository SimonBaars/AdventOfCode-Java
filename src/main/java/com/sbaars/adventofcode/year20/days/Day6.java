package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    return Arrays.stream(day().split("\n\n"))
        .map(i -> i.replace("\n", ""))
        .mapToLong(i -> i.chars().distinct().count()).sum();
  }

  @Override
  public Object part2() {
    return Arrays.stream(day().split("\n\n")).mapToInt(group -> {
      String[] people = group.split("\n");
      List<Integer> c = people[0].chars().boxed().collect(toCollection(ArrayList::new));
      range(1, people.length).forEach(i -> c.retainAll(people[i].chars().boxed().toList()));
      return c.size();
    }).sum();
  }
}
