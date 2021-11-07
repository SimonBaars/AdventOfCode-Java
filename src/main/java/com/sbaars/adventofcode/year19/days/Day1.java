package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;

public class Day1 extends Day2019 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    return dayIntStream().map(this::getFuel).sum();
  }

  @Override
  public Object part2() {
    return dayIntStream().map(this::getRequiredFuel).sum();
  }

  private int getRequiredFuel(int mass) {
    int fuel = getFuel(mass);
    return fuel > 0 ? fuel + getRequiredFuel(fuel) : 0;
  }

  private int getFuel(int mass) {
    return (mass / 3) - 2;
  }
}
