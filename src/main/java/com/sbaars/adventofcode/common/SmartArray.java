package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.common.map.LongCountMap;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A fixed length array where all operations are O(1)
 */
public class SmartArray<T> {
  private final T[] elements;
  private final Map<T, Integer> elementIndex = new HashMap<>();
  private int size;

  public SmartArray(T[] elements) {
    this.elements = elements;
    for (int i = 0; i < elements.length; i++) {
      elementIndex.put(elements[i], i);
    }
    this.size = elements.length;
  }

  public void move(int oldIndex, int newIndex) {
    elementIndex.put(elements[oldIndex], newIndex);
    elementIndex.put(elements[newIndex], oldIndex);
    T old = elements[oldIndex];
    elements[oldIndex] = elements[newIndex];
    elements[newIndex] = old;
  }

  public T get(int i) {
    return elements[i];
  }

  public int size() {
    return size;
  }

  public int indexOf(T element) {
    return elementIndex.getOrDefault(element, -1);
  }

  public void remove(int index) {
    move(index, --size);
  }

  public void add(T element) {
    if (size >= elements.length) throw new IllegalStateException("This array is full!");
    elements[size] = element;
    elementIndex.put(element, size);
    size++;
  }

  public boolean removeElement(T element) {
    if (elementIndex.containsKey(element)) {
      remove(indexOf(element));
      elementIndex.remove(element);
      return true;
    }
    return false;
  }

  public boolean contains(T element) {
    return elementIndex.containsKey(element);
  }

  @SuppressWarnings("unchecked")
  public T[] toArray() {
    if (size == elements.length) return elements;
    T[] arr = (T[]) new Object[size];
    System.arraycopy(elements, 0, arr, 0, size);
    return arr;
  }

  public Stream<T> stream() {
    return Stream.of(toArray());
  }

  @Override
  public String toString() {
    return Arrays.toString(toArray());
  }

  public static <T> Collector<T, List<T>, SmartArray<T>> toSmartArray() {
    final Supplier<List<T>> supplier = ArrayList::new;
    final BiConsumer<List<T>, T> accumulator = List::add;
    final BinaryOperator<List<T>> combiner = (a, b) -> {a.addAll(b); return a;};
    final Function<List<T>, SmartArray<T>> finisher = l -> new SmartArray<>(l.toArray((T[])new Object[0]));
    return Collector.of(supplier, accumulator, combiner, finisher);
  }
}
