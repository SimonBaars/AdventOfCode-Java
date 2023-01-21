package com.sbaars.adventofcode.common.map;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class ListMap<K, V> extends HashMap<K, List<V>> {
	public ListMap() {
	}

	public ListMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ListMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ListMap(Map<? extends K, ? extends List<V>> m) {
		super(m);
	}
	
	public List<V> addTo(K key, V value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new ArrayList<>();
		l.add(value);
        return super.put(key, l);
    }
	
	public List<V> addTo(K key, V[] value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new ArrayList<>();
		Collections.addAll(l, value);
        return super.put(key, l);
    }

	public List<V> addTo(K key, Collection<V> value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key);
		} else l = new ArrayList<>();
		l.addAll(value);
		return super.put(key, l);
	}

	public ListMap<K, V> mergeWith(ListMap<K, V> map) {
		map.forEach(this::addTo);
		return this;
	}

	public ListMap<K, V> removeFrom(K key, V value) {
		get(key).remove(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<V> get(Object key){
		if(!super.containsKey(key))
			super.put((K) key, new ArrayList<>());
		return super.get(key);
	}
	
	public Entry<K, List<V>> getEntryForValue(V i) {
		System.out.println(Arrays.toString(values().toArray()));
		Optional<Entry<K, List<V>>> findAny = entrySet().stream().filter(e -> e.getValue().contains(i)).findAny();
		if(!findAny.isPresent())
			throw new IllegalAccessError("Value "+i+" does not exist in this map!");
		return findAny.get();
	}

	public Stream<V> valueStream() {
		return values().stream().flatMap(List::stream);
	}

	public boolean hasValue(V v) {
		return values().stream().anyMatch(e -> e.contains(v));
	}

	public static <T, K, U> Collector<T, ?, ListMap<K, U>> toListMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
		final Supplier<ListMap<K, U>> supplier = ListMap::new;
		final BiConsumer<ListMap<K, U>, T> accumulator = (a, b) -> a.addTo(keyMapper.apply(b), valueMapper.apply(b));
		final BinaryOperator<ListMap<K, U>> combiner = ListMap::mergeWith;
		final Function<ListMap<K, U>, ListMap<K, U>> finisher = Function.identity();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}
}