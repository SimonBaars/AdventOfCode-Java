package com.sbaars.adventofcode.common;

public class AtomicDouble {
  double d;

  public AtomicDouble(double d) {
    this.d = d;
  }

  public void set(double d) {
    this.d = d;
  }

  public double get() {
    return d;
  }
}
