package com.sbaars.adventofcode.common.location;

import com.sbaars.adventofcode.common.Direction;

public class BoundedLoc extends Loc {
  private final long boundX;
  private final long boundY;

  public BoundedLoc(long x, long y, long boundX, long boundY) {
    super(x, y);
    this.boundX = boundX;
    this.boundY = boundY;
  }

  @Override
  public BoundedLoc move(int dx, int dy) {
    long newX = (x + dx) % boundX;
    long newY = (y + dy) % boundY;
    if (newX < 0) newX += boundX;
    if (newY < 0) newY += boundY;
    return new BoundedLoc(newX, newY, boundX, boundY);
  }

  @Override
  public BoundedLoc move(long dx, long dy) {
    long newX = (x + dx) % boundX;
    long newY = (y + dy) % boundY;
    if (newX < 0) newX += boundX;
    if (newY < 0) newY += boundY;
    return new BoundedLoc(newX, newY, boundX, boundY);
  }

  @Override
  public BoundedLoc move(Loc l) {
    return move(l.x, l.y);
  }

  @Override
  public BoundedLoc move(Direction d) {
    Loc newLoc = super.move(d);
    long newX = newLoc.x % boundX;
    long newY = newLoc.y % boundY;
    if (newX < 0) newX += boundX;
    if (newY < 0) newY += boundY;
    return new BoundedLoc(newX, newY, boundX, boundY);
  }
}
