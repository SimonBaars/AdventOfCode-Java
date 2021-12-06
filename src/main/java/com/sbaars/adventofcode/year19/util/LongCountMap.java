package com.sbaars.adventofcode.year19.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LongCountMap<K> extends HashMap<K, Long> {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public LongCountMap() {
  }

  public LongCountMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public LongCountMap(int initialCapacity) {
    super(initialCapacity);
  }

  public LongCountMap(Map<? extends K, Long> m) {
    super(m);
  }

  public Long increment(K key) {
    return super.put(key, super.containsKey(key) ? super.get(key) + 1 : 1);
  }

  public Long increment(K key, long amount) {
    return super.put(key, super.containsKey(key) ? super.get(key) + amount : amount);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Long get(Object key) {
    if (!super.containsKey(key))
      super.put((K) key, 0L);
    return super.get(key);
  }

  @Override
  public String toString() {
    return keySet().stream().sorted().map(e -> e + "\t" + get(e)).collect(Collectors.joining(System.lineSeparator()));
  }

  public void addAll(LongCountMap<K> amountPerCloneClassSize) {
    amountPerCloneClassSize.entrySet().stream().forEach(e -> this.increment(e.getKey(), e.getValue()));
  }

  public void incrementAll(LongCountMap<K> input) {
    for (Entry<K, Long> i : input.entrySet()) {
      increment(i.getKey(), i.getValue());
    }
  }

  public long sumValues() {
    return values().stream().mapToLong(e -> e).sum();
  }

  public static LongCountMap<Long> ofFrequencies(LongStream frequencies) {
    var lcm = new LongCountMap<Long>();
    frequencies.forEach(lcm::increment);
    return lcm;
  }
}
