package com.sbaars.adventofcode.common;

import java.util.Objects;
import java.util.function.BiFunction;

public class MutableElement<E> implements Comparable<MutableElement<E>> {
  private E e;

  public MutableElement() {
    this.e = null;
  }

  public MutableElement(E e) {
    this.e = e;
  }

  public static <E> MutableElement<E> mutable() {
    return new MutableElement<>();
  }

  public static <E> MutableElement<E> mutable(E e) {
    return new MutableElement<>(e);
  }

  @Override
  public int compareTo(MutableElement<E> t) {
    if (e instanceof Comparable && t.e instanceof Comparable) {
      return ((Comparable) e).compareTo(t.e);
    }
    return 0;
  }

  public E get() {
    return e;
  }

  public void set(E e) {
    this.e = e;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (MutableElement) obj;
    return Objects.equals(this.e, that.e);
  }

  @Override
  public int hashCode() {
    return Objects.hash(e);
  }
}
