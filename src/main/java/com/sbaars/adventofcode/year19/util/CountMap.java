package com.sbaars.adventofcode.year19.util;

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

  public Integer increment(K key) {
    return super.put(key, super.containsKey(key) ? super.get(key) + 1 : 1);
  }

  public Integer increment(K key, int amount) {
    return super.put(key, super.containsKey(key) ? super.get(key) + amount : amount);
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
}
