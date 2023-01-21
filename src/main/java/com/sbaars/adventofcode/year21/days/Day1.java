package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year21.Day2021;

import java.util.stream.IntStream;

public class Day1 extends Day2021 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    long[] input = dayNumbers();
    return IntStream.range(1, input.length).filter(i -> input[i - 1] < input[i]).count();
  }

  @Override
  public Object part2() {
    long[] input = dayNumbers();
    return IntStream.range(3, input.length).filter(i -> input[i - 3] + input[i - 2] + input[i - 1] < input[i] + input[i - 2] + input[i - 1]).count();
  }
}
