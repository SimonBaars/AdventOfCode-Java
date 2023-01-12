package com.sbaars.adventofcode.common.map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CountMap<K> extends HashMap<K, Integer> {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public CountMap() {
  }

  public CountMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public CountMap(int initialCapacity) {
    super(initialCapacity);
  }

  public CountMap(Map<? extends K, Integer> m) {
    super(m);
  }

  public int increment(K key) {
    int newVal = containsKey(key) ? super.get(key) + 1 : 1;
    put(key, newVal);
    return newVal;
  }

  public int increment(K key, int amount) {
    int newVal = containsKey(key) ? super.get(key) + amount : amount;
    put(key, newVal);
    return newVal;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Integer get(Object key) {
    if (!super.containsKey(key))
      super.put((K) key, 0);
    return super.get(key);
  }

  @Override
  public String toString() {
    return keySet().stream().sorted().map(e -> e + "\t" + get(e)).collect(Collectors.joining(System.lineSeparator()));
  }

  public void addAll(CountMap<K> amountPerCloneClassSize) {
    amountPerCloneClassSize.entrySet().stream().forEach(e -> this.increment(e.getKey(), e.getValue()));
  }

  public void incrementAll(CountMap<K> input) {
    for (Entry<K, Integer> i : input.entrySet()) {
      increment(i.getKey(), i.getValue());
    }
  }

  public int sum() {
    return values().stream().mapToInt(e -> e).sum();
  }
}
