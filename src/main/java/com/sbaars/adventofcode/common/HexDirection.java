package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.common.location.Loc3D;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public enum HexDirection {
  NORTHWEST(1, "nw"), EAST(4, "e"), SOUTHEAST(2, "se"), WEST(3, "w"),
  NORTHEAST(4, "ne"), SOUTHWEST(5, "sw");

  public final int num;
  public final String code;

  HexDirection(int num, String code) {
    this.num = num;
    this.code = code;
  }

  public static Optional<HexDirection> get(String code) {
    return Arrays.stream(values()).filter(e -> e.code.equals(code)).findAny();
  }

  public Point move(Point currentLocation, int amount) {
    return switch (this) {
      case EAST -> new Point(currentLocation.x + (amount * 2), currentLocation.y);
      case WEST -> new Point(currentLocation.x - (amount * 2), currentLocation.y);
      case SOUTHWEST -> new Point(currentLocation.x - amount, currentLocation.y + amount);
      case NORTHEAST -> new Point(currentLocation.x + amount, currentLocation.y - amount);
      case SOUTHEAST -> new Point(currentLocation.x + amount, currentLocation.y + amount);
      case NORTHWEST -> new Point(currentLocation.x - amount, currentLocation.y - amount);
    };
  }

  public Loc3D move(Loc3D currentLocation, int amount) {
    return switch (this) {
      case EAST -> new Loc3D(currentLocation.x + amount, currentLocation.y, currentLocation.z);
      case WEST -> new Loc3D(currentLocation.x - amount, currentLocation.y, currentLocation.z);
      case SOUTHWEST -> new Loc3D(currentLocation.x, currentLocation.y + amount, currentLocation.z);
      case NORTHEAST -> new Loc3D(currentLocation.x, currentLocation.y - amount, currentLocation.z);
      case SOUTHEAST -> new Loc3D(currentLocation.x, currentLocation.y, currentLocation.z + amount);
      case NORTHWEST -> new Loc3D(currentLocation.x, currentLocation.y, currentLocation.z - amount);
    };
  }

  public Point move(Point currentLocation) {
    return move(currentLocation, 1);
  }

  public HexDirection opposite() {
    switch (this) {
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case NORTHEAST:
        return SOUTHWEST;
      case SOUTHWEST:
        return NORTHEAST;
      case SOUTHEAST:
        return NORTHWEST;
      case NORTHWEST:
        return SOUTHEAST;
    }
    throw new IllegalStateException("Non-existent Direction: " + this);
  }
}