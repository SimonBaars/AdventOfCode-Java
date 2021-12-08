package com.sbaars.adventofcode.common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * A fixed length array where all operations are O(1)
 */
public class LongArray {
  private final long[] elements;
  private final Map<Long, Integer> elementIndex = new HashMap<>();
  private int size;

  public LongArray(long[] elements) {
    this.elements = elements;
    for (int i = 0; i < elements.length; i++) {
      elementIndex.put(elements[i], i);
    }
    this.size = elements.length;
  }

  public LongArray(LongStream elements) {
    this(elements.toArray());
  }

  public void move(int oldIndex, int newIndex) {
    elementIndex.put(elements[oldIndex], newIndex);
    elementIndex.put(elements[newIndex], oldIndex);
    long old = elements[oldIndex];
    elements[oldIndex] = elements[newIndex];
    elements[newIndex] = old;
  }

  public long get(int i) {
    return elements[i];
  }

  public int size() {
    return size;
  }

  public int indexOf(long element) {
    return elementIndex.get(element);
  }

  public void remove(int index) {
     move(index, --size);
  }

  public boolean removeElement(long element) {
    if(elementIndex.containsKey(element)) {
      remove(indexOf(element));
      elementIndex.remove(element);
      return true;
    }
    return false;
  }

  public boolean contains(long element){
    return elementIndex.containsKey(element);
  }

  public long[] toArray() {
    if(size == elements.length) return elements;
    long[] arr = new long[size];
    System.arraycopy(elements, 0, arr, 0, size);
    return arr;
  }

  public LongStream stream() {
    return LongStream.of(toArray());
  }
}
