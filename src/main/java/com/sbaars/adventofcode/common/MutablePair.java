package com.sbaars.adventofcode.common;

import java.util.Objects;
import java.util.function.BiFunction;

public class MutablePair<A, B> implements Comparable<MutablePair<A, B>> {
  private A a;
  private B b;

  public MutablePair(A a, B b) {
    this.a = a;
    this.b = b;
  }

  public static <A, B> MutablePair<A, B> pair(A a, B b) {
    return new MutablePair<>(a, b);
  }

  public A getLeft() {
    return a;
  }

  public B getRight() {
    return b;
  }

  public <C, D> MutablePair<C, D> map(BiFunction<A, B, MutablePair<C, D>> func) {
    return func.apply(a(), b());
  }

  @Override
  public int compareTo(MutablePair<A, B> t) {
    if (a instanceof Comparable && t.a instanceof Comparable) {
      return ((Comparable) a).compareTo(t.a);
    }
    return 0;
  }

  public A a() {
    return a;
  }

  public B b() {
    return b;
  }

  public void setA(A a) {
    this.a = a;
  }

  public void setB(B b) {
    this.b = b;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (MutablePair) obj;
    return Objects.equals(this.a, that.a) &&
        Objects.equals(this.b, that.b);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b);
  }

  @Override
  public String toString() {
    return "MutablePair[" +
        "a=" + a + ", " +
        "b=" + b + ']';
  }

  public void set(A a, B b) {
    setA(a);
    setB(b);
  }
}
