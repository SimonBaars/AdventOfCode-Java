package com.sbaars.adventofcode.year21.days;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.sbaars.adventofcode.year21.Day2021;
import java.util.stream.LongStream;

public class Day7 extends Day2021 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    return input().map(this::sol).min().getAsLong();
  }

  private LongStream input() {
    return dayNumberStream(",");
  }

  private long sol(long guess){
    return getSteps(guess).sum();
  }

  private LongStream getSteps(long guess) {
    return input().map(n -> max(guess, n) - min(guess, n));
  }

  private long sol2(long guess){
    return getSteps(guess).map(n -> n*(n+1)/2).sum();
  }

  @Override
  public Object part2() {
    return input().map(this::sol2).min().getAsLong();
  }
}
