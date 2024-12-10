package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.common.location.Loc3D;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Direction3D {
  NORTH(1, 'N'), EAST(4, 'E'), SOUTH(2, 'S'), WEST(3, 'W'), DOWN(4, 'D'), UP(5, 'U'), CENTER(6, 'C');

  public final int num;
  public final int code;

  Direction3D(int num, char code) {
    this.num = num;
    this.code = code;
  }

  public static Direction3D[] sixDirections() {
    return new Direction3D[]{NORTH, EAST, SOUTH, WEST, DOWN, UP};
  }

  public static Stream<Direction3D> six() {
    return Arrays.stream(sixDirections());
  }

  public Loc3D move(Loc3D currentLocation, int amount) {
    return switch (this) {
      case SOUTH -> new Loc3D(currentLocation.x, currentLocation.y + amount, currentLocation.z);
      case NORTH -> new Loc3D(currentLocation.x, currentLocation.y - amount, currentLocation.z);
      case EAST -> new Loc3D(currentLocation.x + amount, currentLocation.y, currentLocation.z);
      case WEST -> new Loc3D(currentLocation.x - amount, currentLocation.y, currentLocation.z);
      case UP -> new Loc3D(currentLocation.x, currentLocation.y, currentLocation.z + amount);
      case DOWN -> new Loc3D(currentLocation.x, currentLocation.y, currentLocation.z - amount);
      case CENTER -> new Loc3D(currentLocation.x, currentLocation.y, currentLocation.z);
    };
  }

  public Loc3D move(Loc3D currentLocation) {
    return move(currentLocation, 1);
  }
}
