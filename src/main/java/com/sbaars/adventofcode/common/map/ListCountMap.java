package com.sbaars.adventofcode.common.map;

import java.util.*;

public class ListCountMap<K, V> extends HashMap<K, CountMap<V>> {
	public ListCountMap() {
	}

	public ListCountMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ListCountMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ListCountMap(Map<? extends K, ? extends CountMap<V>> m) {
		super(m);
	}
	
	public int increment(K key, V value) {
		if(super.containsKey(key)) {
			return super.get(key).increment(value);
		}
		CountMap<V> cm = new CountMap<>();
		put(key, cm);
		return cm.increment(value);
    }

	public int increment(K key, V value, int howMuch) {
		return super.containsKey(key) ? super.get(key).increment(value, howMuch) : Objects.requireNonNull(put(key, new CountMap<>())).increment(value, howMuch);
	}

	public ListCountMap<K, V> removeFrom(K key, V value) {
		get(key).remove(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public CountMap<V> get(Object key){
		if(!super.containsKey(key))
			super.put((K) key, new CountMap<>());
		return super.get(key);
	}

	public int get(K key, V value){
		if(!super.containsKey(key))
			super.put(key, new CountMap<>());
		return super.get(key).get(value);
	}
}