package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day13 extends Day2024 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  @Override
  public Object part1() {
    return solve(0);
  }

  @Override
  public Object part2() {
    return solve(10_000_000_000_000L);
  }

  private long solve(long offset) {
    return dayStream("\n\n")
        .map(s -> readString(s, "Button A: X+%n, Y+%n\nButton B: X+%n, Y+%n\nPrize: X=%n, Y=%n", Data.class))
        .map(m -> m.withPrize(m.prizeX() + offset, m.prizeY() + offset))
        .mapToLong(Data::fewestTokens)
        .sum();
  }

  public record Data(long aX, long aY, long bX, long bY, long prizeX, long prizeY) {
    public Data withPrize(long prizeX, long prizeY) {
      return new Data(aX, aY, bX, bY, prizeX, prizeY);
    }

    public long fewestTokens() {
      long numerator = prizeX * aY - prizeY * aX;
      long b = numerator / (bX * aY - bY * aX);
      long remX = prizeX - b * bX;
      long l = aX == 0 ? prizeY : remX;
      long r = aX == 0 ? aY : aX;
      long a = l / r;
      return (a * aY + b * bY == prizeY && l % r == 0) ? 3 * a + b : 0;
    }
  }
}
