package com.sbaars.adventofcode.year19.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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

  public LongCountMap<K> incrementAll(LongCountMap<K> input) {
    for (Entry<K, Long> i : input.entrySet()) {
      increment(i.getKey(), i.getValue());
    }
    return this;
  }

  public long sumValues() {
    return values().stream().mapToLong(e -> e).sum();
  }

  public static LongCountMap<Long> ofFrequencies(LongStream frequencies) {
    var lcm = new LongCountMap<Long>();
    frequencies.forEach(lcm::increment);
    return lcm;
  }

  public static<T> Collector<T, LongCountMap<T>, LongCountMap<T>> toCountMap() {
    final Supplier<LongCountMap<T>> supplier = LongCountMap::new;
    final BiConsumer<LongCountMap<T>, T> accumulator = LongCountMap::increment;
    final BinaryOperator<LongCountMap<T>> combiner = LongCountMap::incrementAll;
    final Function<LongCountMap<T>, LongCountMap<T>> finisher = LongCountMap::new;
    return Collector.of(supplier, accumulator, combiner, finisher);
  }
}
