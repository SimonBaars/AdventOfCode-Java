package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 extends Day2022 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    return performOp(this::signalStrength).mapToLong(e -> e).sum();
  }

  @Override
  public Object part2() {
    return performOp(this::getPixel).collect(Collectors.joining());
  }

  private long signalStrength(long cycle, long x) {
    return (cycle+20) % 40 == 0 ? cycle*x : 0;
  }

  private String getPixel(long cycle, long x) {
    long i = (cycle -1) % 40;
    return (i == 0 ? "\n" : "") + (List.of(x -1, x, x +1).contains(i) ? "#" : ".");
  }

  private<T> Stream<T> performOp(BiFunction<Long, Long, T> func) {
    long cycle = 1;
    long x  = 1;
    List<T> res = new ArrayList<>();
    for(String op : dayStrings()) {
      res.add(func.apply(cycle, x));
      if(op.equals("noop")) {
        cycle++;
      } else if(op.startsWith("addx")) {
        long add = Long.parseLong(op.substring(5));
        cycle++;
        res.add(func.apply(cycle, x));
        cycle++;
        x+=add;
      }
    }
    return res.stream();
  }
}
