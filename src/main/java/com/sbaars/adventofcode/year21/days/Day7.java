package com.sbaars.adventofcode.year21.days;

import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.year21.Day2021;
import java.io.IOException;
import java.math.BigInteger;
import java.util.stream.LongStream;

public class Day7 extends Day2021 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) throws IOException {
    new Day7().printParts();
    System.in.read();
//    new D?ay7().submitPart1();
    new Day7().submitPart2();
  }

  @Override
  public Object part1() {
    var in = dayNumbers(",");

    return LongStream.of(in).map(e -> sol(in, e)).min().getAsLong();
  }

  private long sol(long[] nums, long guess){
    long sol = 0;
    for(long n : nums){
      sol += guess > n ? guess-n : n-guess;
    }
    return sol;
  }

  private BigInteger sol2(long[] nums, long guess){
    BigInteger sol = new BigInteger("0");
    for(long n : nums){
      sol = sol.add(guess > n ? fac(toIntExact(guess-n)) : fac(toIntExact(n-guess)));
    }
    return sol;
  }

  public BigInteger fac(long n) {
    return new BigInteger(Long.toString(n*(n+1)/2));
  }

  @Override
  public Object part2() {
    var in = dayNumbers(",");

    return LongStream.of(in).mapToObj(e -> sol2(in, e)).reduce((a,b) -> a.compareTo(b) == -1 ? a : b).get();
  }
}
