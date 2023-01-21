package com.sbaars.adventofcode.common;

import java.util.HashMap;
import java.util.Map;
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
    return elementIndex.get(element);
  }

  public void remove(int index) {
     move(index, --size);
  }

  public void add(T element) {
    if(size >= elements.length) throw new IllegalStateException("This array is full!");
    elements[size] = element;
    elementIndex.put(element, size);
    size++;
  }

  public boolean removeElement(T element) {
    if(elementIndex.containsKey(element)) {
      remove(indexOf(element));
      elementIndex.remove(element);
      return true;
    }
    return false;
  }

  public boolean contains(T element){
    return elementIndex.containsKey(element);
  }

  @SuppressWarnings("unchecked")
  public T[] toArray() {
    if(size == elements.length) return elements;
    T[] arr = (T[])new Object[size];
    System.arraycopy(elements, 0, arr, 0, size);
    return arr;
  }

  public Stream<T> stream() {
    return Stream.of(toArray());
  }
}
