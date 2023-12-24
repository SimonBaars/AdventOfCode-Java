package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year23.Day2023;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day24 extends Day2023 {

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  public record Input(Loc3D pos, Loc3D vel) {
    public Input(long posX, long posY, long posZ, long velX, long velY, long velZ) {
      this(new Loc3D(posX, posY, posZ), new Loc3D(velX, velY, velZ));
    }
  }

  @Override
  public Object part1() {
    var in = dayStream().map(s -> readString(s, "%n, %n, %n @ %n, %n, %n", Input.class)).toList();
    return "";
  }

  @Override
  public Object part2() {
    return "";
  }
}
