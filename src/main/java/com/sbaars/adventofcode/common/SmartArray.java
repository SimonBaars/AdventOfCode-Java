package com.sbaars.adventofcode.common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * A fixed length array where all operations are O(1)
 */
public class SmartArray {
  private final int[] elements;
  private final Map<Integer, Integer> elementIndex = new HashMap<>();
  private int size;

  public SmartArray(int[] elements) {
    this.elements = elements;
    for (int i = 0; i < elements.length; i++) {
      elementIndex.put(elements[i], i);
    }
    this.size = elements.length;
  }

  public SmartArray(IntStream elements) {
    this(elements.toArray());
  }

  public void move(int oldIndex, int newIndex) {
    elementIndex.put(elements[oldIndex], newIndex);
    elementIndex.put(elements[newIndex], oldIndex);
    int old = elements[oldIndex];
    elements[oldIndex] = elements[newIndex];
    elements[newIndex] = old;
  }

  public int get(int i) {
    return elements[i];
  }

  public int size() {
    return size;
  }

  public int indexOf(int element) {
    return elementIndex.get(element);
  }

  public void remove(int index) {
     move(index, --size);
  }

  public boolean removeElement(int element) {
    if(elementIndex.containsKey(element)) {
      remove(indexOf(element));
      elementIndex.remove(element);
      return true;
    }
    return false;
  }

  public boolean contains(int element){
    return elementIndex.containsKey(element);
  }

  public int[] toArray() {
    if(size == elements.length) return elements;
    int[] arr = new int[size];
    System.arraycopy(elements, 0, arr, 0, size);
    return arr;
  }

  public IntStream stream() {
    return IntStream.of(toArray());
  }
}
