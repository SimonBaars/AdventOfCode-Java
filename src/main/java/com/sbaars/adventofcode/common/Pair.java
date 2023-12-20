package com.sbaars.adventofcode.common;

import java.util.function.BiFunction;

public record Pair<A, B>(A a, B b) implements Comparable<Pair<A, B>> {

  public static <A, B> Pair<A, B> pair(A a, B b) {
    return new Pair<>(a, b);
  }

  public A getLeft() {
    return a;
  }

  public B getRight() {
    return b;
  }

  public <C> C map(BiFunction<A, B, C> func) {
    return func.apply(a(), b());
  }

  @Override
  public int compareTo(Pair<A, B> t) {
    if (a instanceof Comparable && t.a instanceof Comparable) {
      return ((Comparable) a).compareTo(t.a);
    }
    return 0;
  }
}
